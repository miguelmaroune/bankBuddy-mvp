package com.sg.bankBuddy.bankBuddy_core.domain.model;

import java.util.Set;

public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Set<Account> accounts;

    public Client() {}
public Client(Long id, String firstName, String lastName, String email, String address, Set<Account> accounts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String address;
        private Set<Account> accounts;

        public ClientBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ClientBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ClientBuilder accounts(Set<Account> accounts) {
            this.accounts = accounts;
            return this;
        }

        public Client build() {
            return new Client(id, firstName, lastName, email, address, accounts);
        }
    }
}
