package com.sg.bankBuddy.bankBuddy_core.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.application.service.DepositTransactionService;
import com.sg.bankBuddy.bankBuddy_core.application.service.TransactionService;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.PendingState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.RejectedState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.TransactionContext;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.ValidState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.AccountDepositLimitValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.AmountValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.ValidationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepositTransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ObjectFactory<TransactionContext> transactionContextFactory;

    private DepositTransactionService depositTransactionService;

    private Account mockAccount;
    private Transaction mockTransaction;
    private UUID accountId;
    private BigDecimal depositAmount;

    private PendingState pendingState;
    private ValidState validState;
    private RejectedState rejectedState;

    private ValidationHandler depositValidationChain;

    @BeforeEach
    public void setUp() {
        accountId = UUID.randomUUID();
        depositAmount = BigDecimal.valueOf(100);

        mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setBalance(BigDecimal.valueOf(500));

        mockTransaction = Transaction.builder()
                .account(mockAccount)
                .amount(depositAmount)
                .status(TransactionStatus.PENDING)
                .type(TransactionType.DEPOSIT)
                .build();

        pendingState = new PendingState();
        validState = new ValidState();
        rejectedState = new RejectedState();

        depositValidationChain = createDepositValidationChain();

        TransactionContext mockTransactionContext = createTransactionContext(mockTransaction, depositValidationChain);
        when(transactionContextFactory.getObject()).thenReturn(mockTransactionContext);

        when(transactionService.initTransaction(depositAmount, mockAccount, TransactionType.DEPOSIT))
                .thenReturn(mockTransaction);

        depositTransactionService = new DepositTransactionService(
                transactionService,
                accountRepository,
                transactionRepository,
                transactionContextFactory,
                depositValidationChain
        );
    }

    private TransactionContext createTransactionContext(Transaction transaction, ValidationHandler validationHandler) {
        TransactionContext transactionContext = new TransactionContext(pendingState, validState, rejectedState);
        transactionContext.setTransaction(transaction);
        transactionContext.setValidationHandler(validationHandler);
        return transactionContext;
    }

    private ValidationHandler createDepositValidationChain() {
        ValidationHandler amountValidator = new AmountValidator();
        ValidationHandler accountDepositLimitValidator = new AccountDepositLimitValidator();
        amountValidator.linkWith(accountDepositLimitValidator);
        return amountValidator;
    }

    @Test
    public void testDepositTransaction_Success() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = depositTransactionService.deposit(accountId, depositAmount);

        verify(accountRepository).findById(accountId);
        verify(transactionService).initTransaction(depositAmount, mockAccount, TransactionType.DEPOSIT);
        verify(transactionRepository).save(mockTransaction);
        verify(transactionService).updateAccountBalance(mockAccount, BigDecimal.valueOf(600));

        assert result.getStatus() == TransactionStatus.VALID;
        assert result.getAmount().compareTo(depositAmount) == 0;
    }

    @Test
    public void testDepositTransaction_DepositLimitExceeded() {
        mockAccount.setBalance(BigDecimal.valueOf(1000000));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = depositTransactionService.deposit(accountId, depositAmount);

        verify(accountRepository).findById(accountId);
        verify(transactionService).initTransaction(depositAmount, mockAccount, TransactionType.DEPOSIT);
        verify(transactionRepository).save(mockTransaction);

        assert result.getStatus() == TransactionStatus.REJECTED;
        assert result.getDescription().equals("FUNDS_LIMIT_EXCEEDED");
    }
}
