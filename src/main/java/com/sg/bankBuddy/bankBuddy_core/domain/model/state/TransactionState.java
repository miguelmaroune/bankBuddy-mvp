package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

public interface TransactionState {
    void handle(TransactionContext transaction);
}
