package com.lisdev.transactionalapi.application.service;

import com.lisdev.transactionalapi.application.mapper.MovementMapper;
import com.lisdev.transactionalapi.domain.Messages;
import com.lisdev.transactionalapi.domain.exception.AccountNotFoundException;
import com.lisdev.transactionalapi.domain.exception.InsufficientFundsException;
import com.lisdev.transactionalapi.application.port.in.MovementPortIn;
import com.lisdev.transactionalapi.application.port.in.command.TransactionCommand;
import com.lisdev.transactionalapi.application.port.out.AccountPersistencePort;
import com.lisdev.transactionalapi.application.port.out.MovementPersistencePort;
import com.lisdev.transactionalapi.common.UseCase;
import com.lisdev.transactionalapi.domain.model.Account;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.domain.model.MovementType;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class MovementService implements MovementPortIn {

    private final AccountPersistencePort accountPersistencePort;
    private final MovementPersistencePort movementPersistencePort;
    private final MovementMapper movementMapper;

    @Override
    public Mono<Movement> withdrawal(TransactionCommand body) {
        log.info(Messages.START + "withdrawal(identification:{})",
                body.getIdentification());
        return requireActiveAccount(body)
                .flatMap(account -> executeWithdrawal(body, account))
                .doOnNext(m -> log.info(Messages.END + "withdrawal(identification:{})",
                        body.getIdentification()));
    }

    @Override
    public Mono<Movement> deposit(TransactionCommand body) {
        log.info(Messages.START + "deposit(identification:{})",
        		body.getAccountNumber());
        return requireActiveAccount(body)
                .flatMap(account -> executeDeposit(body, account))
                .doOnNext(m -> log.info(Messages.END + "deposit(identification:{})",
                        body.getIdentification()));
    }

    private Mono<Account> requireActiveAccount(TransactionCommand body) {
        return accountPersistencePort
                .findActiveAccountByAccountNumber(body.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException()));
    }

    private Mono<Movement> executeWithdrawal(TransactionCommand body, Account account) {
        BigDecimal balance = Objects.requireNonNullElse(account.getBalance(), BigDecimal.ZERO);
        BigDecimal amount = body.getAmount();
        if (!hasAvailableBalance(balance, amount)) {
            return Mono.error(new InsufficientFundsException(body.getAccountNumber(), balance, amount));
        }
        BigDecimal newBalance = balance.subtract(amount);
        return persistAccountMovement(body, account, MovementType.Withdrawal, newBalance);
    }

    private Mono<Movement> executeDeposit(TransactionCommand body, Account account) {
        BigDecimal newBalance =
                Objects.requireNonNullElse(account.getBalance(), BigDecimal.ZERO).add(body.getAmount());
        return persistAccountMovement(body, account, MovementType.Deposit, newBalance);
    }

    private Mono<Movement> persistAccountMovement(
            TransactionCommand body, Account account, MovementType type, BigDecimal newBalance) {
        account.applyTransaction(body.getIdentification(), newBalance);
        return accountPersistencePort
                .save(account)
                .flatMap(saved -> movementPersistencePort.save(movementMapper.toNewMovement(
                        body, saved.getId(), newBalance, type.getId(), type.getDescription())));
    }

    public Boolean hasAvailableBalance(BigDecimal balance, BigDecimal withdrawalAmount) {
        return withdrawalAmount.compareTo(balance) <= 0;
    }
}
