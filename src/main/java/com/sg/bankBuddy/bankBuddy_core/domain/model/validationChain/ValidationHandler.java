package com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

public abstract class ValidationHandler {

    private ValidationHandler next;

    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public void validate(Transaction transaction) {
        if (doValidate(transaction) && next != null) {
            next.validate(transaction);
        }
    }

    public abstract boolean doValidate(Transaction transaction);

}
