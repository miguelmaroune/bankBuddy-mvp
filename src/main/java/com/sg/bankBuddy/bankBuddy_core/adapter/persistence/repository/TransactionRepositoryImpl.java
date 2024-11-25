package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.TransactionEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.TransactionEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.TransactionRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionRepositoryImpl(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionEntityMapper.toEntity(transaction);
        TransactionEntity savedTransaction = jpaTransactionRepository.save(transactionEntity);
        return TransactionEntityMapper.toDomain(savedTransaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return jpaTransactionRepository.findById(id)
                .map(TransactionEntityMapper::toDomain);
    }
}
