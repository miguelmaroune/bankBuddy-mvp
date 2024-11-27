package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.account.id = :accountId AND t.timeStamp >= :startDate AND t.status = :status")
    List<TransactionEntity> findByAccountId(
            @Param("accountId") UUID accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("status") String status,
            Sort sort
    );
}
