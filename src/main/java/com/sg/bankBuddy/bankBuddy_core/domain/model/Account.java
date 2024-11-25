package com.sg.bankBuddy.bankBuddy_core.domain.model;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.AccountType;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private UUID id;
    private AccountType type;
    private Currency currency;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private Long clientId;
    private Client client;

    public Account() {}

    public Account(UUID id, AccountType type, Currency currency, BigDecimal balance, LocalDateTime createdAt, Long clientId, Client client) {
        this.id = id;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
        this.createdAt = createdAt;
        this.clientId = clientId;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public static class AccountBuilder {
        private UUID id;
        private AccountType type;
        private Currency currency;
        private BigDecimal balance;
        private LocalDateTime createdAt;
        private Long clientId;
        private Client client;

        public AccountBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AccountBuilder type(AccountType type) {
            this.type = type;
            return this;
        }

        public AccountBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public AccountBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountBuilder clientId(Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public AccountBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public Account build() {
            return new Account(id, type, currency, balance, createdAt, clientId, client);
        }
    }
}
