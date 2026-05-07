package com.banking.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(SAVINGS|CHECKING|BUSINESS)$", message = "Invalid account type")
    private String accountType;

    @DecimalMin(value = "0.0", inclusive = true, message = "Initial balance must be non-negative")
    private BigDecimal initialBalance;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter code")
    private String currency;

}
