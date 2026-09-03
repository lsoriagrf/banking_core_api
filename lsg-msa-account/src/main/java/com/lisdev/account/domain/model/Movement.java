package com.lisdev.account.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Movement {

    private Integer id;
    private Integer accountId;
    private Integer transactionTypeId;
    private UUID transactionCode;
    private BigDecimal amount;
    private BigDecimal balance;
    private String note;
    private LocalDateTime createdAt;
    private String createdBy;

    private Movement() {}

    public static Movement createNew(
            Integer accountId,
            Integer transactionTypeId,
            UUID transactionCode,
            BigDecimal amount,
            BigDecimal balance,
            String note,
            String createdBy) {
        Movement movement = new Movement();
        movement.accountId = accountId;
        movement.transactionTypeId = transactionTypeId;
        movement.transactionCode = transactionCode;
        movement.amount = amount;
        movement.balance = balance;
        movement.note = note;
        movement.createdAt = LocalDateTime.now();
        movement.createdBy = createdBy;
        return movement;
    }

    public static Movement rehydrate(
            Integer id,
            Integer accountId,
            Integer transactionTypeId,
            UUID transactionCode,
            BigDecimal amount,
            BigDecimal balance,
            String note,
            LocalDateTime createdAt,
            String createdBy) {
        Movement movement = new Movement();
        movement.id = id;
        movement.accountId = accountId;
        movement.transactionTypeId = transactionTypeId;
        movement.transactionCode = transactionCode;
        movement.amount = amount;
        movement.balance = balance;
        movement.note = note;
        movement.createdAt = createdAt;
        movement.createdBy = createdBy;
        return movement;
    }
}
