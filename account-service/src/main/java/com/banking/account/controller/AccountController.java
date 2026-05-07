package com.banking.account.controller;

import com.banking.account.dto.CreateAccountRequest;
import com.banking.account.dto.AccountResponse;
import com.banking.account.service.AccountService;
import com.banking.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Create new account
     */
    @PostMapping
    @Operation(summary = "Create new account", description = "Create a new bank account for the authenticated user")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateAccountRequest request) {
        log.info("Creating account for user: {}", userId);
        AccountResponse response = accountService.createAccount(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Account created successfully", response));
    }

    /**
     * Get account by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieve account details by account ID")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
        log.info("Fetching account with ID: {}", id);
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account retrieved successfully", response));
    }

    /**
     * Get account by account number
     */
    @GetMapping("/number/{accountNumber}")
    @Operation(summary = "Get account by number", description = "Retrieve account details by account number")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByNumber(@PathVariable String accountNumber) {
        log.info("Fetching account with number: {}", accountNumber);
        AccountResponse response = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account retrieved successfully", response));
    }

    /**
     * Get all accounts for user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user accounts", description = "Retrieve all accounts for a specific user")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getUserAccounts(@PathVariable Long userId) {
        log.info("Fetching all accounts for user: {}", userId);
        List<AccountResponse> responses = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Accounts retrieved successfully", responses));
    }

    /**
     * Get all active accounts for user
     */
    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active accounts", description = "Retrieve all active accounts for a specific user")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getUserActiveAccounts(@PathVariable Long userId) {
        log.info("Fetching active accounts for user: {}", userId);
        List<AccountResponse> responses = accountService.getUserActiveAccounts(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Active accounts retrieved successfully", responses));
    }

    /**
     * Update account
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update account", description = "Update account details")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody CreateAccountRequest request) {
        log.info("Updating account with ID: {}", id);
        AccountResponse response = accountService.updateAccount(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account updated successfully", response));
    }

    /**
     * Get account balance
     */
    @GetMapping("/{id}/balance")
    @Operation(summary = "Get account balance", description = "Retrieve current balance of an account")
    public ResponseEntity<ApiResponse<BigDecimal>> getAccountBalance(@PathVariable Long id) {
        log.info("Fetching balance for account: {}", id);
        BigDecimal balance = accountService.getAccountBalance(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Balance retrieved successfully", balance));
    }

    /**
     * Deactivate account
     */
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate account", description = "Deactivate a bank account")
    public ResponseEntity<ApiResponse<AccountResponse>> deactivateAccount(@PathVariable Long id) {
        log.info("Deactivating account with ID: {}", id);
        AccountResponse response = accountService.deactivateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account deactivated successfully", response));
    }

    /**
     * Activate account
     */
    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate account", description = "Activate a bank account")
    public ResponseEntity<ApiResponse<AccountResponse>> activateAccount(@PathVariable Long id) {
        log.info("Activating account with ID: {}", id);
        AccountResponse response = accountService.activateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account activated successfully", response));
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the service is running")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Account Service is running");
    }

}
