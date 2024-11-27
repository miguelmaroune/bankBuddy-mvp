package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

public class PendingState implements TransactionState {

    @Override
    public void handle(TransactionContext transactionContext) {

        if (transactionContext.validateTransaction()) {
            transactionContext.moveToValid();
        } else {
            transactionContext.moveToRejected();
        }
    }
}
