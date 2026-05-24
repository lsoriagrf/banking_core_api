package com.lisdev.transactionalapi.domain.model;

import com.lisdev.transactionalapi.domain.exception.AccountHasBalanceException;
import com.lisdev.transactionalapi.domain.exception.AccountStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Account {

    private Integer id;
    private String accountNumber;
    private Integer customerId;
    private Integer accountTypeId;
    private String accountType;
    private BigDecimal balance;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Account() {}

    public static String generateAccountNumber() {
        return String.valueOf(
                Math.abs(UUID.randomUUID().getMostSignificantBits() % 9_000_000_000L) + 1_000_000_000L);
    }

    public static Account createNew(
            Integer customerId,
            Integer accountTypeId,
            BigDecimal balance,
            String createdBy) {
        Account account = new Account();
        account.accountNumber = generateAccountNumber();
        account.customerId = customerId;
        account.accountTypeId = accountTypeId;
        account.balance = balance != null ? balance : BigDecimal.ZERO;
        account.status = true;
        account.createdAt = LocalDateTime.now();
        account.createdBy = createdBy;
        return account;
    }

    public static Account rehydrate(
            Integer id,
            String accountNumber,
            Integer customerId,
            Integer accountTypeId,
            String accountType,
            BigDecimal balance,
            Boolean status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy) {
        Account account = new Account();
        account.id = id;
        account.accountNumber = accountNumber;
        account.customerId = customerId;
        account.accountTypeId = accountTypeId;
        account.accountType = accountType;
        account.balance = balance != null ? balance : BigDecimal.ZERO;
        account.status = status;
        account.createdAt = createdAt;
        account.updatedAt = updatedAt;
        account.createdBy = createdBy;
        account.updatedBy = updatedBy;
        return account;
    }

    public void applyTransaction(String updatedBy, BigDecimal newBalance) {
        this.balance = newBalance;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }

    public void updateStatus(Boolean status) {
        if (Objects.equals(this.status, status)) {
            throw AccountStatusException.redundantWithRequestedState(status);
        }
        if (Boolean.FALSE.equals(status) && hasPositiveBalance()) {
            throw new AccountHasBalanceException();
        }
        this.status = status;
        this.updatedBy = this.createdBy;
        this.updatedAt = LocalDateTime.now();
    }

    private boolean hasPositiveBalance() {
        return balance.compareTo(BigDecimal.ZERO) > 0;
    }
}
