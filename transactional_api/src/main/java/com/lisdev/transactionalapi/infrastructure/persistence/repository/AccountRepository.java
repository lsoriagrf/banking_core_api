package com.lisdev.transactionalapi.infrastructure.persistence.repository;

import com.lisdev.transactionalapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Integer> {

    Mono<Boolean> existsByCustomerIdAndStatusTrue(Integer customerId);

    Flux<AccountEntity> findByCustomerId(Integer customerId);

    Mono<AccountEntity> findByAccountNumberAndStatusTrue(String accountNumber);
}
