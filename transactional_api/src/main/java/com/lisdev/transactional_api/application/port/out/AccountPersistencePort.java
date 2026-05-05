package com.lisdev.transactional_api.application.port.out;

import com.lisdev.transactional_api.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountPersistencePort {

    Mono<Boolean> existsActiveAccountByCustomerId(Integer customerId);

    Flux<Account> findAccountsByCustomerId(Integer customerId);

    Mono<Account> findActiveAccountByAccountNumber(String accountNumber);

    Mono<Account> findAccountById(Integer id);

    Mono<Account> save(Account account);

}
