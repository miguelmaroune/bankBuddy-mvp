package com.sg.bankBuddy.bankBuddy_core.domain.enums;

public enum Currency {
    USD,
    EUR;

    public static Currency fromString(String currency) {
        for (Currency curr : values()) {
            if (curr.name().equalsIgnoreCase(currency)) {
                return curr;
            }
        }
        //todo : handle the exception
        throw new IllegalArgumentException("Unknown currency: " + currency);
    }
}
