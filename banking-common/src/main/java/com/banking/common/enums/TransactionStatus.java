package com.banking.common.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("Pending", "Transaction is pending"),
    SUCCESS("Completed", "Transaction completed successfully"),
    FAILED("Failed", "Transaction failed");

    private final String displayName;
    private final String description;

    TransactionStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
