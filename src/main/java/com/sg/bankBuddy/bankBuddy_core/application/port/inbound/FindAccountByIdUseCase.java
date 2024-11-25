package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;

import java.util.UUID;

public interface FindAccountByIdUseCase {
    Account findById(UUID id);
}
