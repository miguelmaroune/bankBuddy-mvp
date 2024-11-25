package com.sg.bankBuddy.bankBuddy_core.domain.enums;

public enum TransactionStatus {
    PENDING,
    PROCESSING,
    VALID,
    REJECTED;

    public static TransactionStatus fromString(String status) {
        for (TransactionStatus transactionStatus : values()) {
            if (transactionStatus.name().equalsIgnoreCase(status)) {
                return transactionStatus;
            }
        }
        // Handle exception for invalid status
        throw new IllegalArgumentException("Unknown transaction status: " + status);
    }
}
