package com.pragma.userservice.application.handler;

import com.pragma.userservice.application.dto.UserDTO;
import jakarta.validation.Valid;

public interface IUserHandler {
    void createOwner(@Valid UserDTO userDTO);
}
