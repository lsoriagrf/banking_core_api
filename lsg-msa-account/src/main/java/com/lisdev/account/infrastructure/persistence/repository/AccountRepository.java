package com.lisdev.account.infrastructure.persistence.repository;

import com.lisdev.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Integer> {

    Flux<AccountEntity> findByCustomerId(Integer customerId);

    Mono<AccountEntity> findByAccountNumberAndStatusTrue(String accountNumber);
}
