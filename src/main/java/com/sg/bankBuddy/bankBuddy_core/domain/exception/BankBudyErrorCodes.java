package com.sg.bankBuddy.bankBuddy_core.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BankBudyErrorCodes {
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "The account does not have enough balance."),
    INVALID_TRANSACTION("INVALID_TRANSACTION", "The transaction is not valid."),
    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND", "The Client could not be found."),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "The account could not be found.");

    private String code;
    private String message;

    public static BankBudyErrorCodes getByteCode(String code){
        return Arrays.stream(BankBudyErrorCodes.values())
                .filter( (error) -> error.getCode().equals(code) )
                .findFirst()
                .orElseThrow();
    }

}
