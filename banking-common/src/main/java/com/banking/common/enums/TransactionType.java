package com.banking.common.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    TRANSFER("Transfer", "Money transfer between accounts"),
    DEPOSIT("Deposit", "Money deposit to account"),
    WITHDRAWAL("Withdrawal", "Money withdrawal from account");

    private final String displayName;
    private final String description;

    TransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
