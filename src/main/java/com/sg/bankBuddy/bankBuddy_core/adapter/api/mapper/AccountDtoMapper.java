package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.AccountDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;

public class AccountDtoMapper {

    public static Account toDomain(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .currency(accountDto.getCurrency())
                .type(accountDto.getType())
                .balance(accountDto.getBalance())
                .build();
    }

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .createdAt(account.getCreatedAt())
                .id(account.getId())
                .type(account.getType())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .build();
    }
}
