package com.pragma.userservice.infrastructure.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.application.handler.IUserHandler;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserHandler userHandler;

    @PostMapping("/owner")
    public ResponseEntity<Void> createOwner(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createOwner(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    public ResponseEntity<Void> createEmployee(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createEmployee(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<Void> createClient(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createClient(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userHandler.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
