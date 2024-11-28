package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.TransactionEntity;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

public class TransactionEntityMapper {


    public static TransactionEntity toEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId())
                .type(transaction.getType().name())
                .account(AccountEntityMapper.toEntity(transaction.getAccount()))
                .status(transaction.getStatus().name())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .description(transaction.getDescription())
                .timeStamp(transaction.getTimeStamp())
                .build();
    }

    public static Transaction toDomain(TransactionEntity transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .type(TransactionType.fromString(transaction.getType()))
                .account(AccountEntityMapper.toDomain(transaction.getAccount()))
                .status(TransactionStatus.fromString(transaction.getStatus()))
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .description(transaction.getDescription())
                .timeStamp(transaction.getTimeStamp())
                .build();
    }

}