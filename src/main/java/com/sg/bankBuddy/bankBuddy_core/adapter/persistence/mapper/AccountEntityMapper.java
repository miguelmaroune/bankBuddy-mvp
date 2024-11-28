package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.AccountEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.ClientEntity;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.AccountType;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.Currency;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

public class AccountEntityMapper {

    public static AccountEntity toEntity(Account account) {
        ClientEntity clientEntity = (account.getClient() != null)
                ? ClientEntity.builder().id(account.getClient().getId()).build()
                : null;

        return AccountEntity.builder()
                .id(account.getId())
                .type(account.getType().name())
                .currency(account.getCurrency().name())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .client(clientEntity)
                .build();
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .type(AccountType.fromString(accountEntity.getType()))
                .currency(Currency.fromString(accountEntity.getCurrency()))
                .balance(accountEntity.getBalance())
                .createdAt(accountEntity.getCreatedAt())
                .client(Client.builder().id(accountEntity.getClient().getId()).build())
                .build();
    }
}
