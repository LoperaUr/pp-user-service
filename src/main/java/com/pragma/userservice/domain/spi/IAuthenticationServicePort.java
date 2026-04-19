package com.pragma.userservice.domain.spi;

import java.util.Optional;

public interface IAuthenticationServicePort {
    Optional<Long> getAuthenticatedUserId();
}

