package com.lisdev.transactionalapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
