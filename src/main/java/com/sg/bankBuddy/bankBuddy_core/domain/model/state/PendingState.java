package com.sg.bankBuddy.bankBuddy_core.domain.model.state;
public class PendingState implements TransactionState {

    @Override
    public void handle(TransactionContext transactionContext) {
        System.out.println("Handling Pending State...");

        if (transactionContext.validateTransaction()) {
            System.out.println("Transaction is valid. Transitioning to ValidState.");
            transactionContext.moveToValid();
        } else {
            System.out.println("Transaction is invalid. Transitioning to RejectedState.");
            transactionContext.moveToRejected();
        }
    }
}
