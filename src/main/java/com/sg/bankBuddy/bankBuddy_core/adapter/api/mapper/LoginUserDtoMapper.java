package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.LoginUserDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.LoginUser;

public class LoginUserDtoMapper {
    public static LoginUser toDomain(LoginUserDto loginUserDto) {
        return LoginUser.builder()
                .email(loginUserDto.getEmail())
                .password(loginUserDto.getPassword())
                .build();
    }

      public static LoginUserDto toDto(LoginUser loginUser) {
        return LoginUserDto.builder()
                .email(loginUser.getEmail())
                .password(loginUser.getPassword())
                .build();
    }
}
