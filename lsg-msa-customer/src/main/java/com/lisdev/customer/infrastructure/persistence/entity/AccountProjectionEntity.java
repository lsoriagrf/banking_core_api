package com.lisdev.customer.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table("account_projection")
public class AccountProjectionEntity {

    @Id
    @Column("account_id")
    private Integer accountId;

    @Column("customer_id")
    private Integer customerId;

    @Column("account_number")
    private String accountNumber;

    @Column("status")
    private Boolean status;

    @Column("last_event_id")
    private UUID lastEventId;

    @Column("last_event_at")
    private LocalDateTime lastEventAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

}
