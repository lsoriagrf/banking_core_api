package com.lisdev.customersapi.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.lisdev.customersapi.domain.Messages;
import com.lisdev.customersapi.domain.exception.ActiveAccountException;
import com.lisdev.customersapi.domain.exception.CustomerAlreadyActiveException;
import com.lisdev.customersapi.domain.exception.CustomerNotFoundException;
import com.lisdev.customersapi.application.mapper.CustomerAuditMapper;
import com.lisdev.customersapi.application.mapper.CustomerMapper;
import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.application.port.out.AccountRestrictionPort;
import com.lisdev.customersapi.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customersapi.application.port.out.CustomerPersistencePort;
import com.lisdev.customersapi.application.port.out.PasswordEncoderPort;
import com.lisdev.customersapi.domain.model.CustomerAuditSnapshot;
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
    @DisplayName("createCustomer: active customer exists → CustomerAlreadyActiveException")
    void createCustomer_whenAlreadyActive_throwsCustomerAlreadyActiveException() {
        CreateCustomerCommand cmd = createCommand(IDENTIFICATION, PASSWORD);
        Customer active = customerRehydrated(CUSTOMER_ID, IDENTIFICATION, true);

        when(customerPersistencePort.findActiveCustomerByIdentification(IDENTIFICATION))
                .thenReturn(Mono.just(active));

        StepVerifier.create(customerService.createCustomer(cmd))
                .expectErrorSatisfies(ex -> assertThat(ex)
                        .isInstanceOf(CustomerAlreadyActiveException.class)
                        .hasMessage(Messages.CUSTOMER_ALREADY_ACTIVE))
                .verify();

        verify(customerPersistencePort).findActiveCustomerByIdentification(IDENTIFICATION);
        verify(passwordEncoderPort, never()).encode(anyString());
        verify(customerPersistencePort, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("createCustomer: deleted customer exists → restore and encode password")
    void createCustomer_whenDeletedExists_restoresAndEncodesPassword() {
        CreateCustomerCommand cmd = createCommand(IDENTIFICATION, PASSWORD);
        Customer deleted = customerRehydrated(CUSTOMER_ID, "old-created-by", false);

        when(customerPersistencePort.findActiveCustomerByIdentification(IDENTIFICATION))
                .thenReturn(Mono.empty());
        when(customerPersistencePort.findDeletedCustomerByIdentification(IDENTIFICATION))
                .thenReturn(Mono.just(deleted));
        doAnswer(inv -> {
            CreateCustomerCommand command = inv.getArgument(0);
            Customer customer = inv.getArgument(1);
            customer.restore(
                    command.identification(),
                    command.firstName(),
                    command.lastName(),
                    command.gender(),
                    command.birthdate(),
                    command.address(),
                    command.phoneNumber());
            return null;
        }).when(customerMapper).restoreEntity(any(CreateCustomerCommand.class), any(Customer.class));
        when(passwordEncoderPort.encode(PASSWORD))
                .thenReturn(ENCODED_PASS);
        when(customerPersistencePort.save(any(Customer.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(customerService.createCustomer(cmd))
                .assertNext(restored -> {
                    assertThat(restored.getPassword()).isEqualTo(ENCODED_PASS);
                    assertThat(restored.getStatus()).isTrue();
                    assertThat(restored.getUpdatedBy()).isEqualTo(IDENTIFICATION);
                })
                .verifyComplete();

        verify(customerMapper).restoreEntity(cmd, deleted);
        verify(passwordEncoderPort).encode(PASSWORD);
        verify(customerPersistencePort).save(deleted);
    }

    @Test
    @DisplayName("createCustomer: new customer → encode password and persist")
    void createCustomer_whenNew_encodesAndSaves() {
        CreateCustomerCommand cmd = createCommand(IDENTIFICATION, PASSWORD);
        Customer mapped = Customer.createNew(
                IDENTIFICATION, "Juan", "Pérez", "M",
                LocalDate.of(1990, 5, 10), "Av. Principal 123", "0987654321");

        when(customerPersistencePort.findActiveCustomerByIdentification(IDENTIFICATION))
                .thenReturn(Mono.empty());
        when(customerMapper.toCustomer(cmd))
                .thenReturn(mapped);
        when(passwordEncoderPort.encode(PASSWORD))
                .thenReturn(ENCODED_PASS);
        when(customerPersistencePort.save(any(Customer.class)))
                .thenAnswer(inv -> {
                    Customer c = inv.getArgument(0);
                    return Mono.just(Customer.rehydrate(
                            42,
                            c.getIdentification(),
                            c.getFirstName(),
                            c.getLastName(),
                            c.getPassword(),
                            c.getGender(),
                            c.getBirthdate(),
                            c.getAddress(),
                            c.getPhoneNumber(),
                            c.getStatus(),
                            c.getCreatedAt(),
                            c.getUpdatedAt(),
                            c.getCreatedBy(),
                            c.getUpdatedBy()));
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

    @Test
    @DisplayName("updateCustomer: customer not found → CustomerNotFoundException")
    void updateCustomer_whenNotFound_throwsException() {
        UpdateCustomerCommand cmd = updateCommand(IDENTIFICATION);
        when(customerPersistencePort.findActiveCustomerById(CUSTOMER_ID))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.updateCustomer(CUSTOMER_ID, cmd))
                .expectErrorSatisfies(ex -> assertThat(ex)
                        .isInstanceOf(CustomerNotFoundException.class)
                        .hasMessage(Messages.CUSTOMER_ID_NOT_EXIST + CUSTOMER_ID))
                .verify();

        verify(customerPersistencePort).findActiveCustomerById(CUSTOMER_ID);
        verify(customerPersistencePort, never()).save(any());
        verify(customerAuditPersistencePort, never()).save(any());
    }

    @Test
    @DisplayName("updateCustomer: customer found → update, save and audit")
    void updateCustomer_whenFound_updatesAndAudits() {
        UpdateCustomerCommand cmd = updateCommand(IDENTIFICATION);
        Customer existing = customerRehydrated(CUSTOMER_ID, "CREATED_BY", true);
        CustomerAuditSnapshot snapshot = snapshotFor(existing);

        when(customerPersistencePort.findActiveCustomerById(CUSTOMER_ID))
                .thenReturn(Mono.just(existing));
        doAnswer(inv -> {
            UpdateCustomerCommand command = inv.getArgument(0);
            Customer customer = inv.getArgument(1);
            customer.update(
                    command.identification(),
                    command.firstName(),
                    command.lastName(),
                    command.gender(),
                    command.birthdate(),
                    command.address(),
                    command.phoneNumber());
            return null;
        }).when(customerMapper).updateEntity(any(UpdateCustomerCommand.class), any(Customer.class));
        when(customerPersistencePort.save(any(Customer.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(customerAuditMapper.toSnapshot(any(Customer.class)))
                .thenReturn(snapshot);
        when(customerAuditPersistencePort.save(any(CustomerAuditSnapshot.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.updateCustomer(CUSTOMER_ID, cmd))
                .assertNext(updated -> {
                    assertThat(updated.getIdentification()).isEqualTo(IDENTIFICATION);
                    assertThat(updated.getUpdatedBy()).isEqualTo(IDENTIFICATION);
                })
                .verifyComplete();

        verify(customerMapper).updateEntity(cmd, existing);
        verify(customerPersistencePort).save(existing);
        verify(customerAuditMapper).toSnapshot(existing);
        verify(customerAuditPersistencePort).save(snapshot);
    }

    @Test
    @DisplayName("deleteCustomer: customer has active accounts → ActiveAccountException")
    void deleteCustomer_whenHasActiveAccounts_throwsActiveAccountException() {
        Customer customer = customerRehydrated(CUSTOMER_ID, "created-by", true);

        when(customerPersistencePort.findActiveCustomerById(CUSTOMER_ID))
                .thenReturn(Mono.just(customer));
        when(accountRestrictionPort.existsActiveAccountsForCustomer(CUSTOMER_ID))
                .thenReturn(Mono.just(true));

        StepVerifier.create(customerService.deleteCustomer(CUSTOMER_ID))
                .expectErrorSatisfies(ex -> assertThat(ex)
                        .isInstanceOf(ActiveAccountException.class)
                        .hasMessage(Messages.ACTIVE_ACCOUNT))
                .verify();

        verify(customerPersistencePort, never()).save(any(Customer.class));
        verify(customerAuditPersistencePort, never()).save(any(CustomerAuditSnapshot.class));
    }

    @Test
    @DisplayName("deleteCustomer: customer found and no active accounts → deactivate and audit")
    void deleteCustomer_whenNoActiveAccounts_deactivatesAndAudits() {
        Customer customer = customerRehydrated(CUSTOMER_ID, "created-by", true);
        CustomerAuditSnapshot snapshot = snapshotFor(customer);

        when(customerPersistencePort.findActiveCustomerById(CUSTOMER_ID))
                .thenReturn(Mono.just(customer));
        when(accountRestrictionPort.existsActiveAccountsForCustomer(CUSTOMER_ID))
                .thenReturn(Mono.just(false));
        when(customerPersistencePort.save(any(Customer.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(customerAuditMapper.toSnapshot(any(Customer.class)))
                .thenReturn(snapshot);
        when(customerAuditPersistencePort.save(any(CustomerAuditSnapshot.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteCustomer(CUSTOMER_ID))
                .verifyComplete();

        assertThat(customer.getStatus()).isFalse();
        assertThat(customer.getUpdatedBy()).isEqualTo("created-by");
        verify(customerPersistencePort).save(customer);
        verify(customerAuditMapper).toSnapshot(customer);
        verify(customerAuditPersistencePort).save(snapshot);
    }

    // ── helpers ───────────────────────────────────────────────────────────

    private static CreateCustomerCommand createCommand(String identification, String password) {
        return new CreateCustomerCommand(
                identification, "Juan", "Pérez", "M",
                LocalDate.of(1990, 5, 10), "Av. Principal 123", "0987654321", password);
    }

    private static UpdateCustomerCommand updateCommand(String identification) {
        return new UpdateCustomerCommand(
                identification, "Juan", "Pérez", "M",
                LocalDate.of(1990, 5, 10), "Av. Principal 123", "0987654321");
    }

    private static Customer customerRehydrated(Integer id, String createdBy, boolean status) {
        return Customer.rehydrate(
                id,
                IDENTIFICATION,
                "Juan",
                "Pérez",
                "OLD_PASS",
                "M",
                LocalDate.of(1990, 5, 10),
                "Av. Principal 123",
                "0987654321",
                status,
                LocalDateTime.of(2020, 1, 1, 0, 0),
                null,
                createdBy,
                null);
    }

    private static CustomerAuditSnapshot snapshotFor(Customer customer) {
        return new CustomerAuditSnapshot(
                customer.getId(),
                customer.getIdentification(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPassword(),
                customer.getGender(),
                customer.getBirthdate(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getUpdatedAt(),
                customer.getUpdatedBy());
    }
}
