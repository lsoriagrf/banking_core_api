package com.lisdev.customer.infrastructure.persistence.repository;

import com.lisdev.customer.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends R2dbcRepository<PersonEntity, Integer> {

    Mono<PersonEntity> findByIdentification(String identification);

}
