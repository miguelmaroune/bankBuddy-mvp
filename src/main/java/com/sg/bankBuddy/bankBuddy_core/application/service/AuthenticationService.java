package com.sg.bankBuddy.bankBuddy_core.application.service;


import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.AuthenticationUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.UserRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.LoginUser;
import com.sg.bankBuddy.bankBuddy_core.domain.model.RegisterUser;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUser input) {
        User user = User
                .builder()
                .email(input.getEmail())
                .fullName(input.getFullName())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();


        return userRepository.save(user);
    }

    public User authenticate(LoginUser input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}