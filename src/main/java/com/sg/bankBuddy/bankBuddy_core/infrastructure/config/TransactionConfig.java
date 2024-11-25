package com.sg.bankBuddy.bankBuddy_core.infrastructure.config;

import com.sg.bankBuddy.bankBuddy_core.domain.model.state.*;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.AccountDepositLimitValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.AmountValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.SufficientAccountBalanceValidator;
import com.sg.bankBuddy.bankBuddy_core.domain.model.validationChain.ValidationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TransactionConfig {

    @Bean
    public PendingState pendingState() {
        return new PendingState();
    }

    @Bean
    public ValidState validState() {
        return new ValidState();
    }

    @Bean
    public RejectedState rejectedState() {
        return new RejectedState();
    }

    @Bean
    @Scope("prototype")
    public TransactionContext transactionContext(
            PendingState pendingState,
            ValidState validState,
            RejectedState rejectedState
    ) {
        return new TransactionContext(pendingState, validState, rejectedState);
    }


    @Bean
    public ValidationHandler depositValidationChain() {
        ValidationHandler amountValidator = new AmountValidator();
        ValidationHandler accountDepositLimitValidator = new AccountDepositLimitValidator();

        amountValidator.linkWith(accountDepositLimitValidator)
                .linkWith(accountDepositLimitValidator);

        return amountValidator;
    }

    @Bean
    public ValidationHandler withdrawalValidationChain() {
        ValidationHandler amountValidator = new AmountValidator();
        ValidationHandler sufficientAccountBalanceValidator = new SufficientAccountBalanceValidator();

        amountValidator.linkWith(sufficientAccountBalanceValidator);

        return amountValidator;
    }

}

