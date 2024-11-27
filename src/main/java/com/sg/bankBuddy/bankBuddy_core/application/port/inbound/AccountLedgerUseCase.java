package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import com.sg.bankBuddy.bankBuddy_core.domain.model.Transaction;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccountLedgerUseCase {
    List<Transaction> findByAccountId(UUID accountId, LocalDateTime from, String status, Sort sort);
}
