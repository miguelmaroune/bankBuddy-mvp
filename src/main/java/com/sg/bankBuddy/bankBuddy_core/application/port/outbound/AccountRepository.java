package com.sg.bankBuddy.bankBuddy_core.application.port.outbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account  save(Account account);

    Optional<Account> findById(UUID id);

}
