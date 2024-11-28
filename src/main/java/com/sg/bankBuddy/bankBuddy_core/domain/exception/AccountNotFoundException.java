package com.sg.bankBuddy.bankBuddy_core.domain.exception;

public class AccountNotFoundException extends RuntimeException {
    private final String message;
    private final String code;

    public AccountNotFoundException(BankBudyErrorCodes error) {
        super(error.getMessage());
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
