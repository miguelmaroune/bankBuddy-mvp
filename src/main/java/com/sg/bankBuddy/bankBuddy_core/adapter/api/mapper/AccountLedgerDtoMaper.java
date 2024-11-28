package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.AccountLedgerDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.time.format.DateTimeFormatter;

public class AccountLedgerDtoMaper {

    public static AccountLedgerDto toDto(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = transaction.getTimeStamp().format(formatter);

        return AccountLedgerDto.builder()
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .transactionDate(formattedDate)
                .build();
    }
}
