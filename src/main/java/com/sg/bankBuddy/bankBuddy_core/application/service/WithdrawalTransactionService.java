package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.WithdrawalTransactionUseCase;
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
public class WithdrawalTransactionService implements WithdrawalTransactionUseCase {
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ObjectFactory<TransactionContext> transactionContextFactory;
    @Qualifier("withdrawalValidationChain")
    private final ValidationHandler withdrawalValidationChain;

    public WithdrawalTransactionService(TransactionService transactionService, AccountRepository accountRepository, TransactionRepository transactionRepository, ObjectFactory<TransactionContext> transactionContextFactory, ValidationHandler withdrawalValidationChain) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionContextFactory = transactionContextFactory;
        this.withdrawalValidationChain = withdrawalValidationChain;
    }


    @Override
    public Transaction withdrawal(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));
        Transaction pendingTransaction = transactionService.initTransaction(amount, account, TransactionType.WITHDRAWAL);

        TransactionContext transactionContext = transactionContextFactory.getObject();
        transactionContext.setTransaction(pendingTransaction);
        transactionContext.setValidationHandler(withdrawalValidationChain);
        transactionContext.request();

        return processTransaction(account, transactionContext);

    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    protected Transaction processTransaction(Account account, TransactionContext transactionContext) {
        if (transactionContext.getTransaction() != null &&
                transactionContext.getTransaction().getStatus().equals(TransactionStatus.VALID)) {
            transactionService.updateAccountBalance(account, account.getBalance().subtract(transactionContext.getTransaction().getAmount()));
        }

        BigDecimal newBalance = account.getBalance().subtract(transactionContext.getTransaction().getAmount());
        transactionContext.getTransaction().setBalance(newBalance);
        return transactionRepository.save(transactionContext.getTransaction());
    }

}
