package com.lisdev.customer.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import com.lisdev.customer.infrastructure.persistence.entity.CustomerAuditEntity;

@Repository
public interface CustomerAuditRepository extends R2dbcRepository<CustomerAuditEntity, Integer> {

}
