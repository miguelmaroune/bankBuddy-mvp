package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;

public class RejectedState implements TransactionState {
    @Override
    public void handle(TransactionContext transaction) {
        //todo: Alert the Fraud risk department , send sms to account holder...
        transaction.getTransaction().setStatus(TransactionStatus.REJECTED);

    }
}
