package com.banking.common.enums;

public enum UserRole {
    ROLE_ADMIN("Administrator"),
    ROLE_USER("Regular User"),
    ROLE_MANAGER("Manager");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
