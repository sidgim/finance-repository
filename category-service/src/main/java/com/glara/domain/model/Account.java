package com.glara.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_account_user", columnList = "user_id"),
        @Index(name = "idx_account_type", columnList = "account_type_id"),
        @Index(name = "idx_account_name", columnList = "name")
})
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_account_type"))
    private AccountType accountType;


    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public Account() {
    }

    public Account(String name, BigDecimal currentBalance, AccountType accountType, UUID userId) {
        this.name = name;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentBalance=" + currentBalance +
                ", accountType=" + accountType +
                ", userId=" + userId +
                ", deleted=" + deleted +
                '}';
    }
}
