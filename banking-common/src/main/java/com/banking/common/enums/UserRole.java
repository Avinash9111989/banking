package com.banking.common.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN", "Administrator"),
    USER("ROLE_USER", "Regular User"),
    MANAGER("ROLE_MANAGER", "Manager");

    private final String code;
    private final String description;

    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
