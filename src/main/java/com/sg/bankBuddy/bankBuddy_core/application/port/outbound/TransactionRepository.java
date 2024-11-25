package com.sg.bankBuddy.bankBuddy_core.application.port.outbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);
}
