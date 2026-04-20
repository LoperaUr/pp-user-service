package com.pragma.userservice.infrastructure.output.security.adapter;

import com.pragma.userservice.domain.constants.DomainConstants;
import com.pragma.userservice.domain.api.IAuthenticationServicePort;
import com.pragma.userservice.infrastructure.output.security.helper.JwtAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationServiceAdapter implements IAuthenticationServicePort {

    @Override
    public Optional<Long> getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthentication jwtAuthentication)) {
            return Optional.empty();
        }

        Object userIdClaim = jwtAuthentication.claims().get(DomainConstants.KEY_USER_ID);
        if (userIdClaim instanceof Number number) {
            return Optional.of(number.longValue());
        }

        if (userIdClaim instanceof String text && !text.isBlank()) {
            try {
                return Optional.of(Long.parseLong(text));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}

