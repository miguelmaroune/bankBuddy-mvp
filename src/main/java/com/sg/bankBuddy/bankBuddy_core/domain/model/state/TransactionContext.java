package com.sg.bankBuddy.bankBuddy_core.domain.model.state;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.ValidationHandler;

public class TransactionContext {

    private TransactionState currentState;
    private final TransactionState pendingState;
    private final TransactionState validState;
    private final TransactionState rejectedState;
    private Transaction transaction;

    private ValidationHandler validationHandler;

    public TransactionContext(
            PendingState pendingState,
            ValidState validState,
            RejectedState rejectedState
    ) {
        this.pendingState = pendingState;
        this.validState = validState;
        this.rejectedState = rejectedState;
        this.currentState = pendingState;
    }

    public void request() {
        currentState.handle(this);
    }

    public void setState(TransactionState newState) {
        this.currentState = newState;
        this.request();
    }

    public TransactionState getCurrentState() {
        return currentState;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void moveToPending() {
        setState(pendingState);
    }

    public void moveToValid() {
        setState(validState);
    }

    public void moveToRejected() {
        setState(rejectedState);
    }

    public void setValidationHandler(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    public boolean validateTransaction() {
        validationHandler.validate(transaction);
        return transaction.getDescription() == null;
    }
}
