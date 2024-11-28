package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateClientDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.CreateClientDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.CreateClientUseCase;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
public class ClientController {
    private final CreateClientUseCase createClientUseCase;

    public ClientController(CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @Operation(summary = "Create a new client", description = "Creates a new client with the provided details.")
    @ApiResponse(responseCode = "201", description = "Client successfully created.")
    @PostMapping
    public ResponseEntity<CreateClientDto> createClient(@RequestBody CreateClientDto createClientDto) {
        Client client = CreateClientDtoMapper.toDomain(createClientDto);
        Client createdClient = createClientUseCase.createClient(client);
        return new ResponseEntity<>(CreateClientDtoMapper.toDto(createdClient), HttpStatus.CREATED);
    }
}
