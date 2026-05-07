package com.banking.common.util;

import java.util.Random;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final Random RANDOM = new Random();
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    /**
     * Generate a unique account number
     */
    public static String generateAccountNumber() {
        long timestamp = System.currentTimeMillis();
        int random = RANDOM.nextInt(10000);
        return String.format("ACC%d%04d", timestamp % 100000000, random);
    }

    /**
     * Generate a unique transaction reference number
     */
    public static String generateTransactionReference() {
        long timestamp = System.currentTimeMillis();
        int random = RANDOM.nextInt(100000);
        return String.format("TXN%d%05d", timestamp % 1000000, random);
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Mask sensitive data (e.g., account number)
     */
    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        String visible = accountNumber.substring(accountNumber.length() - 4);
        return "*".repeat(accountNumber.length() - 4) + visible;
    }

    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        return hasUpperCase && hasLowerCase && hasDigit;
    }
}