package com.sg.bankBuddy.bankBuddy_core.domain.exception;

import java.util.Arrays;

public enum BankBudyErrorCodes {
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "The account does not have enough balance."),
    INVALID_TRANSACTION("INVALID_TRANSACTION", "The transaction is not valid."),
    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND", "The Client could not be found."),
    FUNDS_LIMIT_EXCEEDED("FUNDS_LIMIT_EXCEEDED", "Deposit Limit Exceeded for this account!"),
    INVALID_TRANSACTION_AMOUNT("INVALID_TRANSACTION_AMOUNT", "The amount of the transaction is not valid!"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "The account could not be found.");

    private final String code;
    private final String message;

    BankBudyErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BankBudyErrorCodes getByteCode(String code) {
        return Arrays.stream(BankBudyErrorCodes.values())
                .filter(error -> error.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
