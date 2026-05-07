package com.banking.account.repository;

import com.banking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find account by account number
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Find all accounts by user ID
     */
    List<Account> findByUserId(Long userId);

    /**
     * Find all active accounts by user ID
     */
    @Query("SELECT a FROM Account a WHERE a.userId = :userId AND a.isActive = true")
    List<Account> findActiveAccountsByUserId(@Param("userId") Long userId);

    /**
     * Check if account exists by account number
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Find all active accounts
     */
    @Query("SELECT a FROM Account a WHERE a.isActive = true")
    List<Account> findAllActiveAccounts();

    /**
     * Find accounts by account type and user ID
     */
    List<Account> findByAccountTypeAndUserId(Account.AccountType accountType, Long userId);

    /**
     * Check if user owns the account
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Account a WHERE a.id = :accountId AND a.userId = :userId")
    boolean isAccountOwnedByUser(@Param("accountId") Long accountId, @Param("userId") Long userId);

}
