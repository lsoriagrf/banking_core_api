package com.lisdev.account.application.service;

import com.lisdev.account.domain.Messages;
import com.lisdev.account.domain.exception.AccountNotFoundException;
import com.lisdev.account.domain.exception.CustomerNotFoundException;
import com.lisdev.account.application.mapper.AccountMapper;
import com.lisdev.account.application.port.in.AccountPortIn;
import com.lisdev.account.application.port.in.command.CreateAccountCommand;
import com.lisdev.account.application.port.in.command.FindByAccountNumberCommand;
import com.lisdev.account.application.port.out.AccountPersistencePort;
import com.lisdev.account.application.port.out.CustomerPort;
import com.lisdev.account.common.UseCase;
import com.lisdev.account.domain.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class AccountService implements AccountPortIn {

    private final AccountPersistencePort accountPersistencePort;
    private final CustomerPort customerPort;
    private final AccountMapper accountMapper;

    @Override
    public Mono<Account> createAccount(CreateAccountCommand body) {
        log.info(Messages.START + "createAccount(body:{})", body);
        return customerPort
                .findIdByIdentification(body.getIdentification())
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(body.getIdentification())))
                .map(customerId -> accountMapper.toCreateAccount(body, customerId))
                .flatMap(accountPersistencePort::save)
                .doOnNext(account ->
                        log.info(Messages.END + "createAccount(customerId:{})", account.getCustomerId()));
    }

    @Override
    public Flux<Account> findAccountsByIdentification(String identification) {
        log.info(Messages.START + "findAccountsByIdentification(identification:{})", identification);
        return customerPort
                .findIdByIdentification(identification)
                .flatMapMany(accountPersistencePort::findAccountsByCustomerId)
                .doOnComplete(() -> log.info(
                        Messages.END + "findAccountsByIdentification(identification:{})", identification));
    }

    @Override
    public Mono<Account> updateAccountStatus(Integer id, Boolean status) {
        log.info(Messages.START + "updateAccountStatus(id:{}, status:{})", id, status);
        return accountPersistencePort
                .findAccountById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException()))
                .flatMap(account -> {
                    account.updateStatus(status);
                    return accountPersistencePort.save(account);
                })
                .doOnNext(account -> log.info(Messages.END + "updateAccountStatus(id:{})", account.getId()));
    }

    @Override
    public Mono<Account> findByAccountNumber(FindByAccountNumberCommand body) {
        log.info(Messages.START + "findByAccountNumber(accountNumber:{})", body.getAccountNumber());
        return accountPersistencePort
                .findActiveAccountByAccountNumber(body.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException()))
                .doOnNext(account -> log.info(Messages.END + "findByAccountNumber(accountNumber:{})", 
                		body.getAccountNumber()));
    }

    @Override
    public Mono<Boolean> existsAccountByCustomerId(Integer customerId) {
        return accountPersistencePort.existsActiveAccountByCustomerId(customerId);
    }
}
