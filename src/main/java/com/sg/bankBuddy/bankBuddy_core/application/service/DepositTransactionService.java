package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.DepositTransactionUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.AccountNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import com.sg.bankBuddy.bankBuddy_core.domain.model.state.TransactionContext;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.ValidationHandler;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class DepositTransactionService implements DepositTransactionUseCase {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ObjectFactory<TransactionContext> transactionContextFactory;

    @Qualifier("depositValidationChain")
    private final ValidationHandler depositValidationChain;

    public DepositTransactionService(
            TransactionService transactionService, AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            ObjectFactory<TransactionContext> transactionContextFactory, ValidationHandler depositValidationChain) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionContextFactory = transactionContextFactory;
        this.depositValidationChain = depositValidationChain;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Transaction deposit(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));

        Transaction pendingTransaction = transactionService.initTransaction(amount, account, TransactionType.DEPOSIT);

        TransactionContext transactionContext = transactionContextFactory.getObject();
        transactionContext.setTransaction(pendingTransaction);
        transactionContext.setValidationHandler(depositValidationChain);

        transactionContext.request();

        return processTransaction(account, transactionContext);
    }

    protected Transaction processTransaction(Account account, TransactionContext transactionContext) {
        if (transactionContext.getTransaction() != null &&
                transactionContext.getTransaction().getStatus().equals(TransactionStatus.VALID)) {
            transactionService.updateAccountBalance(account, account.getBalance().add(transactionContext.getTransaction().getAmount()));
        }
        BigDecimal newBalance = account.getBalance();
        transactionContext.getTransaction().setBalance(newBalance);
        return transactionRepository.save(transactionContext.getTransaction());
    }
}
