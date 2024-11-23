package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateClientDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

import java.util.stream.Collectors;

public class CreateClientDtoMapper {

    public static Client toDomain(CreateClientDto createClientDto) {
        return Client.builder()
                .firstName(createClientDto.getFirstName())
                .lastName(createClientDto.getLastName())
                .email(createClientDto.getEmail())
                .address(createClientDto.getAddress())
                .accounts(createClientDto.getAccounts() != null
                        ? createClientDto.getAccounts().stream()
                        .map(CreateAccountDtoMapper::toDomain)
                        .collect(Collectors.toSet())
                        : null)
                .build();
    }

    public static CreateClientDto toDto(Client client) {
        return CreateClientDto.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .address(client.getAddress())
                .accounts(client.getAccounts() != null
                        ? client.getAccounts().stream()
                        .map(CreateAccountDtoMapper::toDto)
                        .collect(Collectors.toSet())
                        : null)
                .build();
    }
}
