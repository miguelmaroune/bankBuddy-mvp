package com.sg.bankBuddy.bankBuddy_core.adapter.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {

    private String message;
    private String errorCode;
}