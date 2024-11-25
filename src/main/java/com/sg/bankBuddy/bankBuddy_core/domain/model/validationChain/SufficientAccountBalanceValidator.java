package com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain;

import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;

public class SufficientAccountBalanceValidator extends ValidationHandler{
    @Override
    protected boolean doValidate(Transaction transaction) {
        boolean isValid;
        Account account = transaction.getAccount();
        BigDecimal transactionAmount = transaction.getAmount();
        BigDecimal currentAmount = account.getBalance();
        isValid =  transactionAmount.compareTo(currentAmount) <= 0;
        if(!isValid){
            transaction.setDescription(BankBudyErrorCodes.INSUFFICIENT_BALANCE.getCode());
        }

        return isValid;
    }
}
