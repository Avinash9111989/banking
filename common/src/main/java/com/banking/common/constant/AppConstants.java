package com.banking.common.constant;

public class AppConstants {
    
    // JWT Constants
    public static final String JWT_SECRET = "${app.jwt.secret:my-super-secret-key-for-jwt-token-generation-and-validation}";
    public static final long JWT_EXPIRATION = 86400000; // 24 hours in milliseconds
    public static final long JWT_REFRESH_EXPIRATION = 604800000; // 7 days in milliseconds
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_HEADER_STRING = "Authorization";

    // API Constants
    public static final String API_VERSION = "/api/v1";
    public static final String USERS_ENDPOINT = API_VERSION + "/users";
    public static final String ACCOUNTS_ENDPOINT = API_VERSION + "/accounts";
    public static final String TRANSACTIONS_ENDPOINT = API_VERSION + "/transactions";

    // Error Messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String INSUFFICIENT_BALANCE = "Insufficient account balance";
    public static final String INVALID_TRANSFER_AMOUNT = "Transfer amount must be greater than 0";
    public static final String SAME_ACCOUNT_TRANSFER = "Cannot transfer to the same account";
    public static final String UNAUTHORIZED_ACCESS = "You don't have permission to access this resource";

    // Success Messages
    public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
    public static final String LOGIN_SUCCESSFUL = "Login successful";
    public static final String ACCOUNT_CREATED_SUCCESSFULLY = "Account created successfully";
    public static final String TRANSACTION_SUCCESSFUL = "Transaction completed successfully";

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int MAX_PAGE_SIZE = 100;

    // Date Format
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
