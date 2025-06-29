package com.pragma.userservice.domain.api;

import com.pragma.userservice.domain.model.User;

public interface IUserServicePort {
    void createOwner(User userEntity);
}
