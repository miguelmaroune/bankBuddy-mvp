package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateAccountDto;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.AccountType;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.Currency;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

public class CreateAccountDtoMapper {

    public static Account toDomain(CreateAccountDto createAccountDto) {
        return Account.builder()
                .type(AccountType.fromString(createAccountDto.getType()))
                .currency(Currency.fromString(createAccountDto.getCurrency()))
                .client(Client.builder().id(createAccountDto.getClientId()).build())
                .build();
    }

    public static CreateAccountDto toDto(Account account) {
        return CreateAccountDto.builder()
                .type(account.getType().name())
                .currency(account.getCurrency().name())
                .clientId(account.getClient().getId())
                .build();
    }
}
