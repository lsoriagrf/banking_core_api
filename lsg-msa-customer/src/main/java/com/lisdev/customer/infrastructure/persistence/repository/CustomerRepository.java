package com.lisdev.customer.infrastructure.persistence.repository;

import com.lisdev.customer.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Integer> {

    Mono<CustomerEntity> findByPersonIdAndStatusTrue(Integer personId);

    Mono<CustomerEntity> findByPersonIdAndStatusFalse(Integer personId);

    Mono<CustomerEntity> findByIdAndStatusTrue(int id);

}
