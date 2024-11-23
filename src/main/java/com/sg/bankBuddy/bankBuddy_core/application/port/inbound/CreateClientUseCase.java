package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

public interface CreateClientUseCase {
    Client createClient(Client client);
}
