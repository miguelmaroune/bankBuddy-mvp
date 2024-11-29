package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.UserEntity;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;

public class UserEntityMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User toDomain(UserEntity user) {
        return User.builder().
                 id(user.getId())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt()).build();
    }

    public static UserEntity toUserDetails(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
