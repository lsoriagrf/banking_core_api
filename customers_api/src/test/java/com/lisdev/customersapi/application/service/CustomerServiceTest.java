package com.lisdev.customersapi.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.lisdev.customersapi.domain.Messages;
import com.lisdev.customersapi.domain.exception.CustomerNotFoundException;
import com.lisdev.customersapi.application.mapper.CustomerAuditMapper;
import com.lisdev.customersapi.application.mapper.CustomerMapper;
import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.application.port.out.AccountRestrictionPort;
import com.lisdev.customersapi.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customersapi.application.port.out.CustomerPersistencePort;
import com.lisdev.customersapi.application.port.out.PasswordEncoderPort;
import com.lisdev.customersapi.domain.model.Customer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    // ── mocks ─────────────────────────────────────────────────────────────
    @Mock private CustomerPersistencePort      customerPersistencePort;
    @Mock private CustomerAuditPersistencePort customerAuditPersistencePort;
    @Mock private AccountRestrictionPort       accountRestrictionPort;
    @Mock private CustomerMapper               customerMapper;
    @Mock private CustomerAuditMapper          customerAuditMapper;
    @Mock private PasswordEncoderPort          passwordEncoderPort;

    @InjectMocks
    private CustomerService customerService;

    // ── constants ─────────────────────────────────────────────────────────
    private static final int    CUSTOMER_ID     = 100;
    private static final String IDENTIFICATION  = "1234567890";
    private static final String PASSWORD        = "secret";
    private static final String ENCODED_PASS    = "ENCODED";

    @BeforeEach
    void setup() {
        lenient().when(customerPersistencePort.findDeletedCustomerByIdentification(anyString()))
                .thenReturn(Mono.empty());
    }

    // ── Tests ────────────────────────────────────────────────────
    @Test
    @DisplayName("updateCustomer: customer not found → CustomerNotFoundException")
    void updateCustomer_whenNotFound_throwsException() {
        UpdateCustomerCommand cmd = updateCommand(CUSTOMER_ID, IDENTIFICATION);
        when(customerPersistencePort.findActiveCustomerById(CUSTOMER_ID))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.updateCustomer(cmd))
                .expectErrorSatisfies(ex -> assertThat(ex)
                        .isInstanceOf(CustomerNotFoundException.class)
                        .hasMessage(Messages.CUSTOMER_ID_NOT_EXIST + CUSTOMER_ID))
                .verify();

        verify(customerPersistencePort).findActiveCustomerById(CUSTOMER_ID);
        verify(customerPersistencePort, never()).save(any());
        verify(customerAuditPersistencePort, never()).save(any());
    }
    
    @Test
    @DisplayName("createCustomer: new customer → encode password and persist")
    void createCustomer_whenNew_encodesAndSaves() {
        CreateCustomerCommand cmd = createCommand(IDENTIFICATION, PASSWORD);
        Customer mapped = customerWithIdentification(IDENTIFICATION);

        when(customerPersistencePort.findActiveCustomerByIdentification(IDENTIFICATION))
                .thenReturn(Mono.empty());
        when(customerMapper.toCustomer(cmd))
                .thenReturn(mapped);
        when(passwordEncoderPort.encode(PASSWORD))
                .thenReturn(ENCODED_PASS);
        when(customerPersistencePort.save(any(Customer.class)))
                .thenAnswer(inv -> {
                    Customer c = inv.getArgument(0);
                    c.setId(42);
                    return Mono.just(c);
                });

        StepVerifier.create(customerService.createCustomer(cmd))
                .assertNext(c -> {
                    assertThat(c.getId()).isEqualTo(42);
                    assertThat(c.getPassword()).isEqualTo(ENCODED_PASS);
                })
                .verifyComplete();

        verify(passwordEncoderPort).encode(PASSWORD);
        verify(customerPersistencePort).save(mapped);
    }

    // ── helpers ───────────────────────────────────────────────────────────
    
    private static Customer customerWithIdentification(String identification) {
        Customer c = new Customer();
        c.setIdentification(identification);
        return c;
    }

    private static CreateCustomerCommand createCommand(String identification, String password) {
        return new CreateCustomerCommand(
                identification, "Juan", "Pérez", "M",
                LocalDate.of(1990, 5, 10), "Av. Principal 123", "0987654321", password);
    }

    private static UpdateCustomerCommand updateCommand(Integer id, String identification) {
        return new UpdateCustomerCommand(
                id, identification, "Juan", "Pérez", "M",
                LocalDate.of(1990, 5, 10), "Av. Principal 123", "0987654321");
    }
}
