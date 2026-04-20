package com.pragma.userservice.domain.api;

import java.util.Optional;

public interface IAuthenticationServicePort {
    Optional<Long> getAuthenticatedUserId();
}

