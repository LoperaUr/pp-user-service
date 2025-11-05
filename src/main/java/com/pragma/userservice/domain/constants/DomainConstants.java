package com.pragma.userservice.domain.constants;

public final class DomainConstants {

    // Prevent instantiation
    private DomainConstants() {}

    // System Messages
    public static final String MSG_USER_NOT_FOUND = "User not found";
    public static final String MSG_INVALID_CELLPHONE = "Invalid cellphone number";
    public static final String MSG_INVALID_DOCUMENT = "Invalid document number";
    public static final String MSG_UNDERAGE_USER = "User must be at least 18 years old";
    public static final String MSG_EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String MSG_PHONE_ALREADY_EXISTS = "Phone number already exists";
    public static final String MSG_INVALID_CREDENTIALS = "Invalid credentials";

    // Key Names
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ROLE_NAME = "role_name";
    public static final String KEY_ERROR = "error";

    // Prefixes
    public static final String TOKEN_PREFIX = "Bearer ";

}
