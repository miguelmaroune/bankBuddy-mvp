package com.sg.bankBuddy.bankBuddy_core.application.port.outbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
