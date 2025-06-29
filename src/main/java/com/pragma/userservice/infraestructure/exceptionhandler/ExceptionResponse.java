package com.pragma.userservice.infraestructure.exceptionhandler;


import lombok.Getter;

@Getter
public enum ExceptionResponse {
    ROLE_NOT_FOUND("Role not found"),
    NOT_FOUND("Not Found")
    ;




    private final String message;
    ExceptionResponse(String message) {
        this.message = message;
    }

}
