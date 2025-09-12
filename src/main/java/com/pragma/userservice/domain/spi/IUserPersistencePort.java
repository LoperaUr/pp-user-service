package com.pragma.userservice.domain.spi;

import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    void saveUser(User userEntity, Role role);

    Optional<User> findById(Long id);
}
