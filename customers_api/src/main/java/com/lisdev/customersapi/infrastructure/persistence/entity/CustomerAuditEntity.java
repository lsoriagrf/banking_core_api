package com.lisdev.customersapi.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table(name = "customer", schema = "audit")
public class CustomerAuditEntity {

    @Id
    @Column("audit_id")
    private Integer auditId;

    @Column("id")
    private Integer id;

    @Column("identification")
    private String identification;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("password")
    private String password;

    @Column("gender")
    private String gender;

    @Column("birthdate")
    private LocalDate birthdate;

    @Column("address")
    private String address;

    @Column("phone_number")
    private String phoneNumber;

    @Column("status")
    private Boolean status;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("updated_by")
    private String updatedBy;

}
