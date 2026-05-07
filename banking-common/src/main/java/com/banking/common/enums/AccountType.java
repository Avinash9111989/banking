package com.banking.common.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    SAVINGS("Savings Account", "For saving purposes"),
    CHECKING("Checking Account", "For daily transactions"),
    BUSINESS("Business Account", "For business operations");

    private final String displayName;
    private final String description;

    AccountType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
