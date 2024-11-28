package com.sg.bankBuddy.bankBuddy_core.application.port.outbound;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.TransactionEntity;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    List<Transaction> findByAccountId(
    UUID accountId,
    LocalDateTime startDate,
    String status,
    Sort sort
    );
}
