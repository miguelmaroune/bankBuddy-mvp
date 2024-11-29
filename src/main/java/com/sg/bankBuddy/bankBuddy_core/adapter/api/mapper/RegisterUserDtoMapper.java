package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.RegisterUserDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.RegisterUser;

public class RegisterUserDtoMapper {
    public static RegisterUser toDomain(RegisterUserDto registerUserDto) {
        return RegisterUser.builder()
                .email(registerUserDto.getEmail())
                .fullName(registerUserDto.getFullName())
                .password(registerUserDto.getPassword())
                .build();
    }

    public static RegisterUserDto toDto(RegisterUser registerUser) {
        return RegisterUserDto.builder()
                .email(registerUser.getEmail())
                .fullName(registerUser.getFullName())
                .password(registerUser.getPassword())
                .build();
    }
}
