package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;

public interface FindAccountByIdUseCase {
    Account findById(Long id);
}
