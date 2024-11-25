package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;

public class PendingState implements TransactionState {

    @Override
    public void handle(TransactionContext transactionContext) {
        System.out.println("Handling Pending State...");

        if (isTransactionValid(transactionContext.getTransaction())) {
            System.out.println("Transaction is valid. Transitioning to ValidState.");
            transactionContext.moveToValid();
        } else {
            System.out.println("Transaction is invalid. Transitioning to RejectedState.");
            transactionContext.moveToRejected();
        }
    }

    private boolean isTransactionValid(Transaction transaction) {
        return transaction != null && transaction.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}

