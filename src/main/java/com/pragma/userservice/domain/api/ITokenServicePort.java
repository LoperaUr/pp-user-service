package com.pragma.userservice.domain.api;

import com.pragma.userservice.domain.model.User;

import java.util.Map;

public interface ITokenServicePort {

    String generateToken(User user);
    Map<String, Object> validateToken(String token);
}
