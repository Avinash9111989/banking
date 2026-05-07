package com.banking.account.service;

import com.banking.account.dto.CreateAccountRequest;
import com.banking.account.dto.AccountResponse;
import com.banking.account.entity.Account;
import com.banking.account.repository.AccountRepository;
import com.banking.common.exception.BadRequestException;
import com.banking.common.exception.ResourceNotFoundException;
import com.banking.common.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Create a new account
     */
    public AccountResponse createAccount(Long userId, CreateAccountRequest request) {
        log.info("Creating account for user: {}", userId);

        // Validate request
        if (request.getInitialBalance() != null && request.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Initial balance cannot be negative");
        }

        // Generate unique account number
        String accountNumber = CommonUtils.generateAccountNumber();
        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = CommonUtils.generateAccountNumber();
        }

        // Create account entity
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setAccountType(Account.AccountType.valueOf(request.getAccountType().toUpperCase()));
        account.setAccountName(request.getAccountName());
        account.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO);
        account.setCurrency(request.getCurrency() != null ? request.getCurrency() : "USD");
        account.setIsActive(true);

        Account savedAccount = accountRepository.save(account);
        log.info("Account created successfully with ID: {} and number: {}", savedAccount.getId(), savedAccount.getAccountNumber());

        return mapToResponse(savedAccount);
    }

    /**
     * Get account by ID
     */
    public AccountResponse getAccountById(Long accountId) {
        log.info("Fetching account with ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
        return mapToResponse(account);
    }

    /**
     * Get account by account number
     */
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        log.info("Fetching account with number: {}", accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
        return mapToResponse(account);
    }

    /**
     * Get all accounts for a user
     */
    public List<AccountResponse> getUserAccounts(Long userId) {
        log.info("Fetching all accounts for user: {}", userId);
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Get all active accounts for a user
     */
    public List<AccountResponse> getUserActiveAccounts(Long userId) {
        log.info("Fetching active accounts for user: {}", userId);
        List<Account> accounts = accountRepository.findActiveAccountsByUserId(userId);
        return accounts.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Update account
     */
    public AccountResponse updateAccount(Long accountId, CreateAccountRequest request) {
        log.info("Updating account with ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (request.getAccountName() != null) {
            account.setAccountName(request.getAccountName());
        }

        Account updatedAccount = accountRepository.save(account);
        log.info("Account updated successfully with ID: {}", accountId);
        return mapToResponse(updatedAccount);
    }

    /**
     * Get account balance
     */
    public BigDecimal getAccountBalance(Long accountId) {
        log.info("Fetching balance for account: {}", accountId);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
        return account.getBalance();
    }

    /**
     * Update account balance (used by transaction service)
     */
    public void updateBalance(Long accountId, BigDecimal amount) {
        log.info("Updating balance for account: {} by amount: {}", accountId, amount);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        log.info("Balance updated successfully for account: {}", accountId);
    }

    /**
     * Deactivate account
     */
    public AccountResponse deactivateAccount(Long accountId) {
        log.info("Deactivating account with ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (!account.getIsActive()) {
            throw new BadRequestException("Account is already inactive");
        }

        account.setIsActive(false);
        Account deactivatedAccount = accountRepository.save(account);
        log.info("Account deactivated successfully with ID: {}", accountId);
        return mapToResponse(deactivatedAccount);
    }

    /**
     * Activate account
     */
    public AccountResponse activateAccount(Long accountId) {
        log.info("Activating account with ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (account.getIsActive()) {
            throw new BadRequestException("Account is already active");
        }

        account.setIsActive(true);
        Account activatedAccount = accountRepository.save(account);
        log.info("Account activated successfully with ID: {}", accountId);
        return mapToResponse(activatedAccount);
    }

    /**
     * Check if user owns the account
     */
    public boolean isAccountOwnedByUser(Long accountId, Long userId) {
        return accountRepository.isAccountOwnedByUser(accountId, userId);
    }

    /**
     * Map Account entity to AccountResponse
     */
    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
            .id(account.getId())
            .userId(account.getUserId())
            .accountNumber(account.getAccountNumber())
            .accountType(account.getAccountType().toString())
            .accountName(account.getAccountName())
            .balance(account.getBalance())
            .currency(account.getCurrency())
            .isActive(account.getIsActive())
            .createdAt(account.getCreatedAt())
            .updatedAt(account.getUpdatedAt())
            .build();
    }

}
