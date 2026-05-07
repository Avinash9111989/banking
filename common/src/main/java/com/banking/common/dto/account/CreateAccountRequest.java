package com.banking.common.dto.account;

import com.banking.common.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    private String currency;
}
