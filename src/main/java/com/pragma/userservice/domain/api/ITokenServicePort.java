package com.pragma.userservice.domain.api;

import java.util.Map;

public interface ITokenServicePort {

    String generateToken(String email);
    String generateToken(String email, Map<String, Object> claims);
    Map<String, Object> validateToken(String token);
}
