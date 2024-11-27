package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.TransactionEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository.JpaTransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.AccountLedgerUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountLedgerService implements AccountLedgerUseCase {

    private final TransactionRepository transactionRepository;

    public AccountLedgerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId, LocalDateTime from, String status, Sort sort) {
        return transactionRepository.findByAccountId(accountId, from, status, sort);
    }
}
