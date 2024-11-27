package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction initTransaction(BigDecimal amount, Account account, TransactionType transactionType) {
        Transaction transaction = Transaction
                .builder()
                .type(transactionType)
                .amount(amount)
                .account(account)
                .status(TransactionStatus.PENDING)
                .timeStamp(LocalDateTime.now())
                .build();
        return transactionRepository.save(transaction);
    }

    public Account updateAccountBalance(Account account, BigDecimal amount) {
        account.setBalance(amount);
        return accountRepository.save(account);
    }

}
