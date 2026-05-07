package com.banking.common.constants;

public class AppConstants {
    // JWT Constants
    public static final String JWT_SECRET = "mySecretKeyForBankingApplicationJWTTokenGenerationAndValidation2024";
    public static final long JWT_EXPIRATION = 86400000; // 24 hours in milliseconds
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

    // API Endpoints
    public static final String API_BASE_PATH = "/api";
    public static final String USERS_ENDPOINT = "/users";
    public static final String ACCOUNTS_ENDPOINT = "/accounts";
    public static final String TRANSACTIONS_ENDPOINT = "/transactions";

    // Error Messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String UNAUTHORIZED = "Unauthorized access";
    public static final String BAD_REQUEST = "Bad request";
    public static final String INSUFFICIENT_BALANCE = "Insufficient balance";

    // Success Messages
    public static final String OPERATION_SUCCESS = "Operation completed successfully";
    public static final String ACCOUNT_CREATED = "Account created successfully";
    public static final String TRANSFER_SUCCESS = "Transfer completed successfully";

    // Validation Constants
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;

    private AppConstants() {
        throw new AssertionError("Cannot instantiate AppConstants");
    }
}