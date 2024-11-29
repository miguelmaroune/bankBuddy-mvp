package com.sg.bankBuddy.bankBuddy_core.adapter.api.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.api.dto.UserDto;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;

public class UserDtoMapper {
    public static User toDomain(UserDto userDto){
        return User.builder().
                id(userDto.getId())
                .fullName(userDto.getFullName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .updatedAt(userDto.getUpdatedAt())
                .createdAt(userDto.getCreatedAt()).build();
    }

    public static UserDto toDto(User user){
        return UserDto.builder().
                id(user.getId())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt()).build();
    }
}
