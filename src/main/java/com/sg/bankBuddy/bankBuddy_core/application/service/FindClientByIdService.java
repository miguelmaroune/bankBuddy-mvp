package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.FindClientByIdUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.ClientRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.ClientNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import org.springframework.stereotype.Service;

@Service
public class FindClientByIdService implements FindClientByIdUseCase {
    private final ClientRepository clientRepository;

    public FindClientByIdService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(BankBudyErrorCodes.CLIENT_NOT_FOUND));
    }
}
