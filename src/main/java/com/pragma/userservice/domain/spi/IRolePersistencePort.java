package com.pragma.userservice.domain.spi;

import com.pragma.userservice.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {
    Optional<Long> findRoleIdByName(Role role);

    void saveRole(Role role);
}

