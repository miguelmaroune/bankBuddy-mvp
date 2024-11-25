package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;

public class ValidState implements TransactionState{
    @Override
    public void handle(TransactionContext transaction) {
        System.out.println("TRANSACTION IS VALIDDDD..");
     transaction.getTransaction().setStatus(TransactionStatus.VALID);
    }
}
