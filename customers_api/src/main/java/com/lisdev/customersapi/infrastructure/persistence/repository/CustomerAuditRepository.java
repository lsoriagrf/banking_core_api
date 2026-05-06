package com.lisdev.customersapi.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import com.lisdev.customersapi.infrastructure.persistence.entity.CustomerAuditEntity;

@Repository
public interface CustomerAuditRepository extends R2dbcRepository<CustomerAuditEntity, Integer> {

}
