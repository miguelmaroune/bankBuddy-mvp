package com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain;

import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;

public class AmountValidator extends ValidationHandler {
    @Override
    public boolean doValidate(Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            transaction.setDescription(BankBudyErrorCodes.INVALID_TRANSACTION_AMOUNT.getCode());
            return false;
        }

        return true;
    }
}
