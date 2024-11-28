package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.ClientDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.CreateClientDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.ClientDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.CreateClientDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.CreateClientUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.FindClientByIdUseCase;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
public class ClientController {
    private final CreateClientUseCase createClientUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;

    public ClientController(CreateClientUseCase createClientUseCase, FindClientByIdUseCase findClientByIdUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.findClientByIdUseCase = findClientByIdUseCase;
    }

    @Operation(summary = "Create a new client", description = "Creates a new client with the provided details.")
    @ApiResponse(responseCode = "201", description = "Client successfully created.")
    @PostMapping
    public ResponseEntity<CreateClientDto> createClient(@RequestBody CreateClientDto createClientDto) {
        Client client = CreateClientDtoMapper.toDomain(createClientDto);
        Client createdClient = createClientUseCase.createClient(client);
        return new ResponseEntity<>(CreateClientDtoMapper.toDto(createdClient), HttpStatus.CREATED);
    }

    @Operation(summary = "Find a client by ID", description = "Retrieves a client based on the provided client ID.")
    @ApiResponse(responseCode = "200", description = "Client found.")
    @ApiResponse(responseCode = "404", description = "Client not found.")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> findClientById(@PathVariable Long clientId) {
        Client client = findClientByIdUseCase.findById(clientId);
        return new ResponseEntity<>(ClientDtoMapper.toDto(client), HttpStatus.OK);
    }
}
