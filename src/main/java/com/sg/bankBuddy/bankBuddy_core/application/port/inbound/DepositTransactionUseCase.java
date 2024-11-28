package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface DepositTransactionUseCase {
    Transaction deposit(UUID accountId, BigDecimal amount);
}
