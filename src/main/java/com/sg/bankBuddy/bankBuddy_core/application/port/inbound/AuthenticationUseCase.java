package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.LoginUser;
import com.sg.bankBuddy.bankBuddy_core.domain.model.RegisterUser;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;

public interface AuthenticationUseCase {
    User signup(RegisterUser input);
    User authenticate(LoginUser input);
}
