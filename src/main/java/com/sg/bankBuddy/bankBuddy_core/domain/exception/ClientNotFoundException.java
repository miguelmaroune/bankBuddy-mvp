package com.sg.bankBuddy.bankBuddy_core.domain.exception;

import lombok.Getter;

public class ClientNotFoundException extends RuntimeException{
    private final String message;
    @Getter
    private final String code;

    public ClientNotFoundException(BankBudyErrorCodes error) {
        super(error.getMessage());
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
