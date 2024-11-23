package com.sg.bankBuddy.bankBuddy_core.domain.enums;

public enum AccountType {
    SAVINGS,
    CURRENT,
    CHECKING;

    public static AccountType fromString(String type) {
        for (AccountType accountType : values()) {
            if (accountType.name().equalsIgnoreCase(type)) {
                return accountType;
            }
        }
        //todo: handle the Exception...
        throw new IllegalArgumentException("Unknown account type: " + type);
    }
}
