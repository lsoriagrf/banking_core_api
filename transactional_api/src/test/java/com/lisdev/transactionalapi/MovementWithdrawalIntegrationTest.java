package com.lisdev.transactionalapi;

import com.lisdev.transactionalapi.application.port.in.MovementPortIn;
import com.lisdev.transactionalapi.application.port.in.command.TransactionCommand;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.infrastructure.persistence.entity.AccountEntity;
import com.lisdev.transactionalapi.infrastructure.persistence.entity.MovementEntity;
import com.lisdev.transactionalapi.infrastructure.persistence.repository.AccountRepository;
import com.lisdev.transactionalapi.infrastructure.persistence.repository.MovementRepository;
import io.r2dbc.spi.ConnectionFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.test.StepVerifier;


class MovementWithdrawalIntegrationTest extends PostgresIntegrationTestBase{
	
	@Autowired private MovementPortIn movementPortIn;
    @Autowired private ConnectionFactory connectionFactory;
    @Autowired private AccountRepository accountRepository;
    @Autowired private MovementRepository movementRepository;

    @BeforeEach
    void setUpSchema() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("/db/schema.sql"),
                new ClassPathResource("/db/data.sql"));
        populator.populate(connectionFactory).block(Duration.ofSeconds(10));
    }

    @Test
    void withdrawal_should_persist_movement_and_update_balance_in_real_database() {
        TransactionCommand command = buildWithdrawalCommand("1717171717", "1234567890", "250.00");
        StepVerifier.create(movementPortIn.withdrawal(command))
                .assertNext(movement -> assertWithdrawalResponse(movement, "250.00", "750.00"))
                .verifyComplete();
        assertAccountUpdated(1, "750.00", "1717171717");
        assertMovementPersisted("1717171717", "250.00", "750.00");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private TransactionCommand buildWithdrawalCommand(String identification, String accountNumber, String amount) {
        TransactionCommand command = new TransactionCommand();
        command.setIdentification(identification);
        command.setAccountNumber(accountNumber);
        command.setAmount(new BigDecimal(amount));
        return command;
    }

    private void assertWithdrawalResponse(Movement movement, String expectedAmount, String expectedBalance) {
        assertNotNull(movement.getTransactionCode());
        assertEquals(new BigDecimal(expectedAmount), movement.getAmount());
        assertEquals(new BigDecimal(expectedBalance), movement.getBalance());
        assertEquals("Retiro", movement.getNote());
    }

    private void assertAccountUpdated(int accountId, String expectedBalance, String expectedUpdatedBy) {
        AccountEntity account = accountRepository.findById(accountId).block(Duration.ofSeconds(5));
        assertNotNull(account);
        assertEquals(new BigDecimal(expectedBalance), account.getBalance());
        assertEquals(expectedUpdatedBy, account.getUpdatedBy());
    }

    private void assertMovementPersisted(String createdBy, String expectedAmount, String expectedBalance) {
        List<MovementEntity> movements = movementRepository.findAll().collectList().block(Duration.ofSeconds(5));
        assertNotNull(movements);
        assertEquals(1, movements.size());
        MovementEntity m = movements.getFirst();
        assertEquals(1, m.getAccountId());
        assertEquals(2, m.getTransactionTypeId());
        assertEquals(new BigDecimal(expectedAmount), m.getAmount());
        assertEquals(new BigDecimal(expectedBalance), m.getBalance());
        assertEquals("Retiro", m.getNote());
        assertEquals(createdBy, m.getCreatedBy());
    }
}
