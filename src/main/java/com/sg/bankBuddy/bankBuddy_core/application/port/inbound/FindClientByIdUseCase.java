package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

public interface FindClientByIdUseCase {
    Client findById(Long id);
}
