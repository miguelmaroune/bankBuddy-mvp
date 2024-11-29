package com.sg.bankBuddy.bankBuddy_core.adapter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;

    private String fullName;

    private String email;

    private String password;

    private Date createdAt;

    private Date updatedAt;
}
