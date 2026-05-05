package com.lisdev.transactional_api.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table("account")
public class AccountEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("account_number")
    private String accountNumber;

    @Column("customer_id")
    private Integer customerId;

    @Column("account_type_id")
    private Integer accountTypeId;

    @Column("balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column("status")
    private Boolean status = true;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_by")
    private String createdBy;

    @Column("updated_by")
    private String updatedBy;

}
