package com.lisdev.transactional_api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.lisdev.transactional_api.application.Messages;
import com.lisdev.transactional_api.application.exception.AccountNotFoundException;
import com.lisdev.transactional_api.application.exception.InsufficientFundsException;
import com.lisdev.transactional_api.application.mapper.MovementMapper;
import com.lisdev.transactional_api.application.port.in.command.TransactionCommand;
import com.lisdev.transactional_api.application.port.out.AccountPersistencePort;
import com.lisdev.transactional_api.application.port.out.MovementPersistencePort;
import com.lisdev.transactional_api.domain.model.Account;
import com.lisdev.transactional_api.domain.model.Movement;
import com.lisdev.transactional_api.domain.model.MovementType;
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

    @Mock private AccountPersistencePort  accountPersistencePort;
    @Mock private MovementPersistencePort  movementPersistencePort;
    @Mock private MovementMapper            movementMapper;
    @InjectMocks private MovementService   movementService;

    // ── helpers ──────────────────────────────────────────────────────────

    private TransactionCommand withdrawalCmd(String acct, BigDecimal amt) {
        TransactionCommand c = new TransactionCommand();
        c.setIdentification("91234326");
        c.setAccountNumber(acct);
        c.setAmount(amt);
        return c;
    }

    private Account activeAccount(String acct, BigDecimal balance) {
        Account a = new Account();
        a.setId(1); a.setAccountNumber(acct);
        a.setBalance(balance); a.setStatus(Boolean.TRUE);
        return a;
    }

    private Movement withdrawalMovement(BigDecimal amount, BigDecimal balance) {
        Movement m = new Movement();
        m.setId(10); m.setAccountId(1);
        m.setTransactionTypeId(MovementType.Withdrawal.getId());
        m.setTransactionCode(UUID.randomUUID());
        m.setAmount(amount); m.setBalance(balance);
        return m;
    }

    private static String insufficientMsg(String acct, BigDecimal bal, BigDecimal amt) {
        return new InsufficientFundsException(acct, bal, amt).getMessage();
    }

    // ── tests ─────────────────────────────────────────────────────────────

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
        Account account            = activeAccount(ACCOUNT_001, BASE_BALANCE);
        BigDecimal expectedBalance = BASE_BALANCE.subtract(WITHDRAW_AMT);
        TransactionCommand cmd     = withdrawalCmd(ACCOUNT_001, WITHDRAW_AMT);
        Movement expected          = withdrawalMovement(WITHDRAW_AMT, expectedBalance);

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
}