package com.sg.bankBuddy.bankBuddy_core.application.port.inbound;

import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionReportUseCase {
    byte[] generateTransactionReport(UUID accountId, LocalDateTime fromDateTime, String type, Sort timeStamp);
}
