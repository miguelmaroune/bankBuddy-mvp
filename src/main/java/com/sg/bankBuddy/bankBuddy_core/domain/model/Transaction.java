package com.sg.bankBuddy.bankBuddy_core.domain.model;

import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionStatus;
import com.sg.bankBuddy.bankBuddy_core.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;
    private Account account;
    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timeStamp;
    private BigDecimal balance;

    public Transaction(Long id, Account account, TransactionType type, TransactionStatus status, BigDecimal amount, String description, LocalDateTime timeStamp, BigDecimal balance) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.timeStamp = timeStamp;
        this.balance = balance;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private Long id;
        private Account account;
        private TransactionType type;
        private TransactionStatus status;
        private BigDecimal amount;
        private String description;
        private LocalDateTime timeStamp;
        private BigDecimal balance;

        public TransactionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public TransactionBuilder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder status(TransactionStatus status) {
            this.status = status;
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionBuilder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public TransactionBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, account, type, status, amount, description, timeStamp, balance);
        }
    }
}
