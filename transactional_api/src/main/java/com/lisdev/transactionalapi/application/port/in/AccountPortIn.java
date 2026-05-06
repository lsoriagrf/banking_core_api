package com.lisdev.transactionalapi.application.port.in;

import com.lisdev.transactionalapi.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactionalapi.application.port.in.command.FindByAccountNumberCommand;
import com.lisdev.transactionalapi.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountPortIn {

    Mono<Boolean> existsAccountByCustomerId(Integer customerId);

    Mono<Account> createAccount(CreateAccountCommand body);

    Flux<Account> findAccountsByIdentification(String identification);

    Mono<Account> updateAccountStatus(Integer id, Boolean status);

    Mono<Account> findByAccountNumber(FindByAccountNumberCommand body);

}
