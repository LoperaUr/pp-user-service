package com.pragma.userservice.infrastructure.output.security.adapter;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.infrastructure.constants.InfrastructureConstants;
import com.pragma.userservice.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordServiceAdapter implements IPasswordServicePort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InfrastructureException(InfrastructureConstants.MSG_PASSWORD_NULL_OR_EMPTY, HttpStatus.BAD_REQUEST);
        }
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            throw new InfrastructureException(InfrastructureConstants.MSG_PASSWORDS_NULL, HttpStatus.BAD_REQUEST);
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
