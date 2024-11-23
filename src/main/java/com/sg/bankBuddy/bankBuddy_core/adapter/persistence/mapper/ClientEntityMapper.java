package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.ClientEntity;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

import java.util.stream.Collectors;

public class ClientEntityMapper {

    public static ClientEntity toEntity(Client client) {
        return ClientEntity.builder()
                .id(client.getId())
                .fistName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .address(client.getAddress())
                .accounts(
                        client.getAccounts() != null
                                ? client.getAccounts().stream()
                                .map(AccountEntityMapper::toEntity)
                                .collect(Collectors.toSet())
                                : null
                )
                .build();
    }

    public static Client toDomain(ClientEntity clientEntity) {
        return Client.builder()
                .id(clientEntity.getId())
                .firstName(clientEntity.getFistName())
                .lastName(clientEntity.getLastName())
                .email(clientEntity.getEmail())
                .address(clientEntity.getAddress())
                .accounts(
                        clientEntity.getAccounts() != null
                                ? clientEntity.getAccounts().stream()
                                .map(AccountEntityMapper::toDomain)
                                .collect(Collectors.toSet())
                                : null
                )
                .build();
    }
}
