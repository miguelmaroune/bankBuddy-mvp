package com.sg.bankBuddy.bankBuddy_core.domain.model;

public class RegisterUser {
    private String email;
    private String password;
    private String fullName;

    public RegisterUser() {
    }

    private RegisterUser(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static RegisterUserBuilder builder() {
        return new RegisterUserBuilder();
    }

    public static class RegisterUserBuilder {
        private String email;
        private String password;
        private String fullName;

        public RegisterUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterUserBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public RegisterUser build() {
            return new RegisterUser(email, password, fullName);
        }
    }
}
