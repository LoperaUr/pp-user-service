package com.pragma.userservice.domain.api;

public interface IPasswordServicePort {
    String encodePassword(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
