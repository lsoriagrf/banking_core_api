package com.lisdev.transactional_api.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table("movements")
public class MovementEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("account_id")
    private Integer accountId;

    @Column("transaction_type_id")
    private Integer transactionTypeId;

    @Column("transaction_code")
    private UUID transactionCode;

    @Column("amount")
    private BigDecimal amount;

    @Column("balance")
    private BigDecimal balance;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("created_by")
    private String createdBy;

}
