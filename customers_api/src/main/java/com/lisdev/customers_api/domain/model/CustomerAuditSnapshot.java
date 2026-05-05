package com.lisdev.customers_api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerAuditSnapshot(
        Integer id,
        String identification,
        String firstName,
        String lastName,
        String password,
        String gender,
        LocalDate birthdate,
        String address,
        String phoneNumber,
        Boolean status,
        LocalDateTime updatedAt,
        String updatedBy) {}
