package com.sg.bankBuddy.bankBuddy_core.adapter.api.dto;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.AccountType;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private UUID id;
    private AccountType type;
    private Currency currency;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
