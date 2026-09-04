package com.lisdev.account.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table("customer_projection")
public class CustomerProjectionEntity {

    @Id
    @Column("customer_id")
    private Integer customerId;

    @Column("identification")
    private String identification;

    @Column("full_name")
    private String fullName;

    @Column("status")
    private Boolean status;

    @Column("last_event_id")
    private UUID lastEventId;

    @Column("last_event_at")
    private LocalDateTime lastEventAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

}
