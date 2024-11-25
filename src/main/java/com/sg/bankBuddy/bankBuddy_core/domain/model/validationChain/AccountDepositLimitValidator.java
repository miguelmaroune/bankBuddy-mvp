package com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain;

import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;

public class AccountDepositLimitValidator extends ValidationHandler{
    @Override
    protected boolean doValidate(Transaction transaction) {
        Account account = transaction.getAccount();
        BigDecimal currentBalance = account.getBalance();
        BigDecimal effectiveBalance = currentBalance.add(transaction.getAmount());
        //todo : Add a deposit Limit ...
//        BigDecimal depositLimitBalance = account.getDepositLimit();


        boolean isValid =  effectiveBalance.compareTo(new BigDecimal(10000)) <= 0;

        if(!isValid){
            transaction.setDescription(BankBudyErrorCodes.FUNDS_LIMIT_EXCEEDED.getCode());
        }
        return isValid;
    }
}
