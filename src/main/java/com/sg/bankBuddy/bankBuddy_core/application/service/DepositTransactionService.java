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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DepositTransactionService implements DepositTransactionUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionContext transactionContext;

    public DepositTransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionContext transactionContext) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionContext = transactionContext;
    }

    @Override
    public Transaction deposit(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));
        Transaction pendingTransaction = initTransaction(amount, account);

        transactionContext.setTransaction(pendingTransaction);
        transactionContext.request();

        return transactionRepository.save(transactionContext.getTransaction());
    }

    private Transaction initTransaction(BigDecimal amount, Account account) {
        Transaction transaction = Transaction
                .builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .account(account)
                .status(TransactionStatus.PENDING)
                .timeStamp(LocalDateTime.now())
                .build();
        return transactionRepository.save(transaction);
    }
}