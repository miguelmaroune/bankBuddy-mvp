package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;

public class RejectedState implements TransactionState{
    @Override
    public void handle(TransactionContext transaction) {
        System.out.println("TRANSACTION IS Rejected..");
        transaction.getTransaction().setStatus(TransactionStatus.REJECTED);

    }
}
