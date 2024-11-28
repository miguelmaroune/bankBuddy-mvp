package com.sg.bankBuddy.bankBuddy_core.application.port.outbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(Long id);


}
