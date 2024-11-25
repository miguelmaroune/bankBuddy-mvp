package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.TransactionDto;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

public class TransactionDtoMapper {


    public static Transaction toDomain(TransactionDto transactionDto) {
        return Transaction.builder()
                .id(transactionDto.getId())
                .type(TransactionType.fromString(transactionDto.getType()))
                .status(TransactionStatus.fromString(transactionDto.getStatus()))
                .amount(transactionDto.getAmount())
                .description(transactionDto.getDescription())
                .timeStamp(transactionDto.getTimeStamp())
                .build();
    }

    public static TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .type(transaction.getType().name())
                .status(transaction.getStatus().name())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .timeStamp(transaction.getTimeStamp())
                .build();
    }
}