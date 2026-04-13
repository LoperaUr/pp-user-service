package com.pragma.userservice.testdata.builders;

import com.pragma.userservice.domain.model.Auth;

public final class AuthBuilder {

    private String email = "john@example.com";
    private String password = "raw123";
    private String token;

    private AuthBuilder() {
    }

    public static AuthBuilder anAuth() {
        return new AuthBuilder();
    }

    public AuthBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public AuthBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AuthBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public Auth build() {
        return new Auth(email, password, token);
    }
}

