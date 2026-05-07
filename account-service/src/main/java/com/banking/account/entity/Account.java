package com.banking.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts", indexes = {
    @Index(name = "idx_accounts_user_id", columnList = "user_id"),
    @Index(name = "idx_accounts_account_number", columnList = "account_number")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, length = 100)
    private String accountName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(length = 3)
    private String currency = "USD";

    @Column(nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private LocalDateTime lastTransactionDate;

    // Enums
    public enum AccountType {
        SAVINGS, CHECKING, BUSINESS
    }

}
