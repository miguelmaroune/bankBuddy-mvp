package com.sg.bankBuddy.bankBuddy_core.domain.model;

public class LoginUser {
    private String email;
    private String password;

    public LoginUser() {
    }

     private LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
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

    public static LoginUserBuilder builder() {
        return new LoginUserBuilder();
    }

     public static class LoginUserBuilder {
        private String email;
        private String password;

        public LoginUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LoginUserBuilder password(String password) {
            this.password = password;
            return this;
        }

       public LoginUser build() {
            return new LoginUser(email, password);
        }
    }
}
