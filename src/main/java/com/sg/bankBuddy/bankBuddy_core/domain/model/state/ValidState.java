package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;

public class ValidState implements TransactionState {
    @Override
    public void handle(TransactionContext transaction) {
        //todo: Send sms / email to account holder. ,
        transaction.getTransaction().setStatus(TransactionStatus.VALID);
    }
}
