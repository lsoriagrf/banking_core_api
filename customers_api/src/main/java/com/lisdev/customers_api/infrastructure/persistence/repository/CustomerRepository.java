package com.lisdev.customers_api.infrastructure.persistence.repository;

import com.lisdev.customers_api.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Integer> {

    Mono<CustomerEntity> findByIdentificationAndStatusTrue(String identification);

    Mono<CustomerEntity> findByIdentificationAndStatusFalse(String identification);

    Mono<CustomerEntity> findByIdAndStatusTrue(int id);

}
