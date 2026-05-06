package com.lisdev.transactionalapi.application.service;

import com.lisdev.transactionalapi.domain.Messages;
import com.lisdev.transactionalapi.domain.exception.AccountHasBalanceException;
import com.lisdev.transactionalapi.domain.exception.AccountNotFoundException;
import com.lisdev.transactionalapi.domain.exception.AccountStatusException;
import com.lisdev.transactionalapi.domain.exception.CustomerNotFoundException;
import com.lisdev.transactionalapi.application.mapper.AccountMapper;
import com.lisdev.transactionalapi.application.port.in.AccountPortIn;
import com.lisdev.transactionalapi.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactionalapi.application.port.in.command.FindByAccountNumberCommand;
import com.lisdev.transactionalapi.application.port.out.AccountPersistencePort;
import com.lisdev.transactionalapi.application.port.out.CustomerPort;
import com.lisdev.transactionalapi.common.UseCase;
import com.lisdev.transactionalapi.domain.model.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
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
                    if (Objects.equals(account.getStatus(), status)) {
                        return Mono.error(AccountStatusException.redundantWithRequestedState(status));
                    }
                    if (Boolean.FALSE.equals(status)) {
                        BigDecimal balance = account.getBalance();
                        if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0) {
                            return Mono.error(new AccountHasBalanceException());
                        }
                    }
                    account.setStatus(status);
                    account.setUpdatedBy(account.getCreatedBy());
                    account.setUpdatedAt(LocalDateTime.now());
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
