package com.sg.bankBuddy.bankBuddy_core.domain.enums;

public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL;

    public static TransactionType fromString(String type) {
        for (TransactionType transactionType : values()) {
            if (transactionType.name().equalsIgnoreCase(type)) {
                return transactionType;
            }
        }
        // Handle exception for invalid type
        throw new IllegalArgumentException("Unknown transaction type: " + type);
    }
}
