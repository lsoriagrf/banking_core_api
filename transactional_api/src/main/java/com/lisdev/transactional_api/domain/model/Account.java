package com.lisdev.transactional_api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public static String generateAccountNumber() {
        return String.valueOf(
                Math.abs(UUID.randomUUID().getMostSignificantBits() % 9_000_000_000L) + 1_000_000_000L);
    }

    public void applyTransaction(String updatedBy, BigDecimal newBalance) {
        this.balance = newBalance;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}
