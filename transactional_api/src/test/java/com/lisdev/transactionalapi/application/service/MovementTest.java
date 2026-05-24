package com.lisdev.transactionalapi.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.lisdev.transactionalapi.domain.Messages;
import com.lisdev.transactionalapi.domain.exception.AccountNotFoundException;
import com.lisdev.transactionalapi.domain.exception.InsufficientFundsException;
import com.lisdev.transactionalapi.application.mapper.MovementMapper;
import com.lisdev.transactionalapi.application.port.in.command.TransactionCommand;
import com.lisdev.transactionalapi.application.port.out.AccountPersistencePort;
import com.lisdev.transactionalapi.application.port.out.MovementPersistencePort;
import com.lisdev.transactionalapi.domain.model.Account;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.domain.model.MovementType;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MovementTest {

    private static final String ACCOUNT_001    = "001";
    private static final String ACCOUNT_MISSING = "999";
    private static final BigDecimal BASE_BALANCE = new BigDecimal("1000.00");
    private static final BigDecimal WITHDRAW_AMT = new BigDecimal("200.00");
    private static final BigDecimal DEPOSIT_AMT  = new BigDecimal("150.00");

    @Mock private AccountPersistencePort  accountPersistencePort;
    @Mock private MovementPersistencePort  movementPersistencePort;
    @Mock private MovementMapper            movementMapper;
    @InjectMocks private MovementService   movementService;

    // ── withdrawal ────────────────────────────────────────────────────────

    @Test
    void withdrawal_accountNotFound_throwsException() {
        TransactionCommand cmd = withdrawalCmd(ACCOUNT_MISSING, new BigDecimal("100.00"));
        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_MISSING))
                .thenReturn(Mono.empty());

        StepVerifier.create(movementService.withdrawal(cmd))
                .expectErrorSatisfies(e -> {
                    assertInstanceOf(AccountNotFoundException.class, e);
                    assertEquals(Messages.ACCOUNT_NOT_FOUND, e.getMessage());
                }).verify();

        verify(accountPersistencePort, never()).save(any());
        verify(movementPersistencePort, never()).save(any());
    }

    @Test
    void withdrawal_insufficientFunds_throwsException() {
        BigDecimal balance = new BigDecimal("500.00");
        BigDecimal amount  = new BigDecimal("1000.00");
        Account account    = activeAccount(ACCOUNT_001, balance);
        TransactionCommand cmd = withdrawalCmd(ACCOUNT_001, amount);

        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_001))
                .thenReturn(Mono.just(account));

        StepVerifier.create(movementService.withdrawal(cmd))
                .expectErrorSatisfies(e -> {
                    assertInstanceOf(InsufficientFundsException.class, e);
                    assertEquals(insufficientMsg(ACCOUNT_001, balance, amount), e.getMessage());
                }).verify();

        verify(movementPersistencePort, never()).save(any());
        verify(accountPersistencePort, never()).save(any());
    }

    @Test
    void withdrawal_success_updatesBalanceAndPersistsMovement() {
        Account account = activeAccount(ACCOUNT_001, BASE_BALANCE);
        BigDecimal expectedBalance = BASE_BALANCE.subtract(WITHDRAW_AMT);
        TransactionCommand cmd = withdrawalCmd(ACCOUNT_001, WITHDRAW_AMT);
        Movement expected = withdrawalMovement(WITHDRAW_AMT, expectedBalance);

        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_001))
                .thenReturn(Mono.just(account));
        when(accountPersistencePort.save(account))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(movementMapper.toNewMovement(eq(cmd), eq(account.getId()),
                eq(expectedBalance), eq(MovementType.Withdrawal.getId()),
                eq(MovementType.Withdrawal.getDescription())))
                .thenReturn(expected);
        when(movementPersistencePort.save(expected))
                .thenReturn(Mono.just(expected));

        StepVerifier.create(movementService.withdrawal(cmd))
                .expectNext(expected)
                .verifyComplete();

        assertEquals(expectedBalance, account.getBalance());
        assertEquals(cmd.getIdentification(), account.getUpdatedBy());
        verify(accountPersistencePort).findActiveAccountByAccountNumber(ACCOUNT_001);
        verify(accountPersistencePort).save(account);
        verify(movementPersistencePort).save(expected);
    }

    // ── deposit ───────────────────────────────────────────────────────────

    @Test
    void deposit_shouldFail_whenAccountNotFound() {
        TransactionCommand cmd = depositCmd(ACCOUNT_MISSING, DEPOSIT_AMT);
        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_MISSING))
                .thenReturn(Mono.empty());

        StepVerifier.create(movementService.deposit(cmd))
                .expectErrorSatisfies(e -> {
                    assertInstanceOf(AccountNotFoundException.class, e);
                    assertEquals(Messages.ACCOUNT_NOT_FOUND, e.getMessage());
                }).verify();

        verify(accountPersistencePort, never()).save(any());
        verify(movementPersistencePort, never()).save(any());
    }

    @Test
    void deposit_shouldIncreaseBalance_withCorrectCalculation() {
        Account account = activeAccount(ACCOUNT_001, BASE_BALANCE);
        BigDecimal expectedBalance = BASE_BALANCE.add(DEPOSIT_AMT);
        TransactionCommand cmd = depositCmd(ACCOUNT_001, DEPOSIT_AMT);
        Movement expected = depositMovement(DEPOSIT_AMT, expectedBalance);

        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_001))
                .thenReturn(Mono.just(account));
        when(accountPersistencePort.save(account))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(movementMapper.toNewMovement(eq(cmd), eq(account.getId()),
                eq(expectedBalance), eq(MovementType.Deposit.getId()),
                eq(MovementType.Deposit.getDescription())))
                .thenReturn(expected);
        when(movementPersistencePort.save(expected))
                .thenReturn(Mono.just(expected));

        StepVerifier.create(movementService.deposit(cmd))
                .expectNext(expected)
                .verifyComplete();

        assertEquals(expectedBalance, account.getBalance());
        assertEquals(cmd.getIdentification(), account.getUpdatedBy());
        verify(accountPersistencePort).save(account);
        verify(movementPersistencePort).save(expected);
    }

    @Test
    void deposit_shouldSucceed_whenAmountEqualsBalance() {
        Account account            = activeAccount(ACCOUNT_001, BASE_BALANCE);
        BigDecimal expectedBalance = BASE_BALANCE.add(BASE_BALANCE);
        TransactionCommand cmd     = depositCmd(ACCOUNT_001, BASE_BALANCE);
        Movement expected          = depositMovement(BASE_BALANCE, expectedBalance);

        when(accountPersistencePort.findActiveAccountByAccountNumber(ACCOUNT_001))
                .thenReturn(Mono.just(account));
        when(accountPersistencePort.save(account))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(movementMapper.toNewMovement(eq(cmd), eq(account.getId()),
                eq(expectedBalance), eq(MovementType.Deposit.getId()),
                eq(MovementType.Deposit.getDescription())))
                .thenReturn(expected);
        when(movementPersistencePort.save(expected))
                .thenReturn(Mono.just(expected));

        StepVerifier.create(movementService.deposit(cmd))
                .expectNext(expected)
                .verifyComplete();

        assertEquals(expectedBalance, account.getBalance());
        verify(accountPersistencePort).save(account);
        verify(movementPersistencePort).save(expected);
    }
    
 // ── helpers ──────────────────────────────────────────────────────────

    private TransactionCommand withdrawalCmd(String acct, BigDecimal amt) {
        TransactionCommand c = new TransactionCommand();
        c.setIdentification("91234326");
        c.setAccountNumber(acct);
        c.setAmount(amt);
        return c;
    }

    private TransactionCommand depositCmd(String acct, BigDecimal amt) {
        TransactionCommand c = new TransactionCommand();
        c.setIdentification("91234326");
        c.setAccountNumber(acct);
        c.setAmount(amt);
        return c;
    }

    private Account activeAccount(String acct, BigDecimal balance) {
        return Account.rehydrate(1, acct, null, null, null, balance, Boolean.TRUE, null, null, null, null);
    }

    private Movement withdrawalMovement(BigDecimal amount, BigDecimal balance) {
        return movementOf(MovementType.Withdrawal, amount, balance);
    }

    private Movement depositMovement(BigDecimal amount, BigDecimal balance) {
        return movementOf(MovementType.Deposit, amount, balance);
    }

    private Movement movementOf(MovementType type, BigDecimal amount, BigDecimal balance) {
        return Movement.rehydrate(10, 1, type.getId(), UUID.randomUUID(), amount, balance, null, null, null);
    }

    private static String insufficientMsg(String acct, BigDecimal bal, BigDecimal amt) {
        return new InsufficientFundsException(acct, bal, amt).getMessage();
    }
}