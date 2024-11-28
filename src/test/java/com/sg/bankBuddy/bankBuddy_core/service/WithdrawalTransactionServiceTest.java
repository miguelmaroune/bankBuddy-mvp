package com.sg.bankBuddy.bankBuddy_core.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.application.service.TransactionService;
import com.sg.bankBuddy.bankBuddy_core.application.service.WithdrawalTransactionService;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.PendingState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.RejectedState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.TransactionContext;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.ValidState;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.AmountValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.SufficientAccountBalanceValidator;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WithdrawalTransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ObjectFactory<TransactionContext> transactionContextFactory;

    private WithdrawalTransactionService withdrawalTransactionService;

    private Account mockAccount;
    private Transaction mockTransaction;
    private UUID accountId;
    private BigDecimal withdrawalAmount;

    private PendingState pendingState;
    private ValidState validState;
    private RejectedState rejectedState;

    private ValidationHandler withdrawalValidationChain;

    @BeforeEach
    public void setUp() {
        accountId = UUID.randomUUID();
        withdrawalAmount = BigDecimal.valueOf(100);

        mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setBalance(BigDecimal.valueOf(500));

        mockTransaction = Transaction.builder()
                .account(mockAccount)
                .amount(withdrawalAmount)
                .status(TransactionStatus.PENDING)
                .type(TransactionType.WITHDRAWAL)
                .build();

        pendingState = new PendingState();
        validState = new ValidState();
        rejectedState = new RejectedState();

        withdrawalValidationChain = createWithdrawalValidationChain();

        TransactionContext mockTransactionContext = createTransactionContext(mockTransaction, withdrawalValidationChain);
        when(transactionContextFactory.getObject()).thenReturn(mockTransactionContext);

        when(transactionService.initTransaction(withdrawalAmount, mockAccount, TransactionType.WITHDRAWAL))
                .thenReturn(mockTransaction);

        withdrawalTransactionService = new WithdrawalTransactionService(
                transactionService,
                accountRepository,
                transactionRepository,
                transactionContextFactory,
                withdrawalValidationChain
        );
    }

    private TransactionContext createTransactionContext(Transaction transaction, ValidationHandler validationHandler) {
        TransactionContext transactionContext = new TransactionContext(pendingState, validState, rejectedState);
        transactionContext.setTransaction(transaction);
        transactionContext.setValidationHandler(validationHandler);
        return transactionContext;
    }

    private ValidationHandler createWithdrawalValidationChain() {
        ValidationHandler amountValidator = new AmountValidator();
        ValidationHandler sufficientAccountBalanceValidator = new SufficientAccountBalanceValidator();
        amountValidator.linkWith(sufficientAccountBalanceValidator);
        return amountValidator;
    }

    @Test
    public void testWithdrawalTransaction_Success() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = withdrawalTransactionService.withdrawal(accountId, withdrawalAmount);

        verify(accountRepository).findById(accountId);
        verify(transactionService).initTransaction(withdrawalAmount, mockAccount, TransactionType.WITHDRAWAL);
        verify(transactionRepository).save(mockTransaction);
        verify(transactionService).updateAccountBalance(mockAccount, BigDecimal.valueOf(400));

        assert result.getStatus() == TransactionStatus.VALID;
        assert result.getAmount().compareTo(withdrawalAmount) == 0;
    }

    @Test
    public void testWithdrawalTransaction_InsufficientFunds() {
        mockAccount.setBalance(BigDecimal.valueOf(50));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = withdrawalTransactionService.withdrawal(accountId, withdrawalAmount);

        verify(accountRepository).findById(accountId);
        verify(transactionService).initTransaction(withdrawalAmount, mockAccount, TransactionType.WITHDRAWAL);
        verify(transactionRepository).save(mockTransaction);
        verify(transactionService, never()).updateAccountBalance(any(), any());

        assert result.getStatus() == TransactionStatus.REJECTED;
        assert result.getDescription().equals("INSUFFICIENT_BALANCE");
    }
}
