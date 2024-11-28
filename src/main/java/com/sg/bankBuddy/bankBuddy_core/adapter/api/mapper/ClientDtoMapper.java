package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.ClientDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

import java.util.stream.Collectors;

public class ClientDtoMapper {
    public static Client toDomain(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .email(clientDto.getEmail())
                .address(clientDto.getAddress())
                .build();
    }

    public static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .address(client.getAddress())
                .build();
    }
}
