package com.sg.bankBuddy.bankBuddy_core.adapter.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLedgerDto {

    String transactionDate;

    BigDecimal amount;

    BigDecimal balance;

}
