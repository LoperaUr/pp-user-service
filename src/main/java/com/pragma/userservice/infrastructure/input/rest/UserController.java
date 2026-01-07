package com.pragma.userservice.infrastructure.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createOwner(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createOwner(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> createEmployee(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createEmployee(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/client")
    @PreAuthorize("permitAll()") // Permitir auto-registro de clientes (HU8)
    public ResponseEntity<Void> createClient(@Valid @RequestBody UserDTO userDTO) {
        userHandler.createClient(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userHandler.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
