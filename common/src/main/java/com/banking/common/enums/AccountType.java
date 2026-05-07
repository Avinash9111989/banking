package com.banking.common.enums;

public enum AccountType {
    SAVINGS("Savings Account"),
    CHECKING("Checking Account"),
    BUSINESS("Business Account");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
