package com.sg.bankBuddy.bankBuddy_core.adapter.api.controller;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.LoginResponseDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.LoginUserDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.RegisterUserDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.UserDto;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.LoginUserDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.RegisterUserDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper.UserDtoMapper;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.UserEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.AuthenticationUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.service.JwtService;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(JwtService jwtService, AuthenticationUseCase authenticationUseCase) {
        this.jwtService = jwtService;
        this.authenticationUseCase = authenticationUseCase;
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user in the system with the provided details."
    )
    @ApiResponse(responseCode = "200", description = "User successfully registered.")
    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationUseCase.signup(RegisterUserDtoMapper.toDomain(registerUserDto));
        return ResponseEntity.ok(UserDtoMapper.toDto(registeredUser));
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates a user with valid credentials and generates a JWT token."
    )
    @ApiResponse(responseCode = "200", description = "User successfully authenticated.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authUser = authenticationUseCase.authenticate(LoginUserDtoMapper.toDomain(loginUserDto));
        UserDetails authenticatedUser = UserEntityMapper.toUserDetails(authUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }
}
