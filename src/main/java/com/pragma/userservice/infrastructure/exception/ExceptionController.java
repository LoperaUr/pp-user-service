package com.pragma.userservice.infrastructure.exception;

import com.pragma.userservice.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.pragma.userservice.domain.constants.DomainConstants.KEY_ERROR;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(KEY_ERROR, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Map<String, String>> handleInfrastructureException(InfrastructureException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(KEY_ERROR, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }
}