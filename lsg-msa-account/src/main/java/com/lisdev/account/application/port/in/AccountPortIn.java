package com.lisdev.account.application.port.in;

import com.lisdev.account.application.port.in.command.CreateAccountCommand;
import com.lisdev.account.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountPortIn {

    Mono<Account> createAccount(CreateAccountCommand body);

    Flux<Account> findAccountsByIdentification(String identification);

    Mono<Account> updateAccountStatus(Integer id, Boolean status);

}
