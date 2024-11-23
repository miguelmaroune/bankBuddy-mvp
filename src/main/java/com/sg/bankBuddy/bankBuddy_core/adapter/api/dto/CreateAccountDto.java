package com.sg.bankBuddy.bankBuddy_core.adapter.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDto {

    @Pattern(regexp = "SAVINGS|CURRENT|CHECKING", message = "Invalid account type")
    private String type;

    @Pattern(regexp = "USD|EUR", message = "Invalid currency, only USD and EUR are allowed")
    private String currency;

    @NotNull(message = "Client ID is required")
    @Min(value = 1, message = "Client ID must be a positive integer")
    private Long clientId;

}
