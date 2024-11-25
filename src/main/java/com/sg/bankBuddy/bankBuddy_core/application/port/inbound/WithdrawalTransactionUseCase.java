package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface WithdrawalTransactionUseCase {
    Transaction withdrawal(UUID accountId , BigDecimal amount);
}
