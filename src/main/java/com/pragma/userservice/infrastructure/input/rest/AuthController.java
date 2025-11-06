package com.pragma.userservice.infrastructure.input.rest;

import com.pragma.userservice.application.dto.AuthDTO;
import com.pragma.userservice.application.handler.IUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserHandler userHandler;

    @PostMapping("/login")
    public AuthDTO login(@RequestBody AuthDTO authDTO) {
        return userHandler.login(authDTO);
    }
}
