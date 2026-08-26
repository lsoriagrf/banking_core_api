package com.lisdev.customersapi.application.service;

import com.lisdev.customersapi.domain.exception.ActiveAccountException;
import com.lisdev.customersapi.domain.exception.CustomerAlreadyActiveException;
import com.lisdev.customersapi.domain.exception.CustomerNotFoundException;
import com.lisdev.customersapi.domain.Messages;
import com.lisdev.customersapi.application.mapper.CustomerAuditMapper;
import com.lisdev.customersapi.application.mapper.CustomerMapper;
import com.lisdev.customersapi.application.port.in.CustomerPortIn;
import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.application.port.out.AccountRestrictionPort;
import com.lisdev.customersapi.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customersapi.application.port.out.CustomerPersistencePort;
import com.lisdev.customersapi.application.port.out.PasswordEncoderPort;
import com.lisdev.customersapi.domain.model.ResolvedActiveCustomer;
import com.lisdev.customersapi.domain.model.Customer;
import com.lisdev.customersapi.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CustomerService implements CustomerPortIn {

    private final CustomerPersistencePort customerPersistencePort;
    private final CustomerAuditPersistencePort customerAuditPersistencePort;
    private final AccountRestrictionPort accountRestrictionPort;
    private final CustomerMapper customerMapper;
    private final CustomerAuditMapper customerAuditMapper;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public Mono<Customer> createCustomer(CreateCustomerCommand command) {
        log.info(Messages.START + "createCustomer(identification:{})", command.identification());
        return customerPersistencePort.findActiveCustomerByIdentification(command.identification())
                .flatMap(active -> Mono.<Customer>error(
                        new CustomerAlreadyActiveException()))
                .switchIfEmpty(
                        customerPersistencePort.findDeletedCustomerByIdentification(command.identification())
                                .flatMap(deleted -> {
                                    customerMapper.restoreEntity(command, deleted);
                                    deleted.assignEncodedPassword(passwordEncoderPort.encode(command.password()));
                                    return customerPersistencePort.save(deleted);
                                })
                                .switchIfEmpty(Mono.<Customer>defer(() -> {
                                    Customer customer = customerMapper.toCustomer(command);
                                    customer.assignEncodedPassword(passwordEncoderPort.encode(command.password()));
                                    return customerPersistencePort.save(customer);
                                })))
                .doOnNext(customer -> log.info(Messages.END + "createCustomer(identification:{})",
                        customer.getIdentification()));
    }

    @Override
    public Mono<Customer> updateCustomer(UpdateCustomerCommand command) {
        log.info(Messages.START + "updateCustomer(id:{}, identification:{})",
                command.id(), command.identification());
        return customerPersistencePort.findActiveCustomerById(command.id())
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(command.id())))
                .flatMap(customer -> {
                    customerMapper.updateEntity(command, customer);
                    return customerPersistencePort.save(customer);
                })
                .flatMap(saved -> customerAuditPersistencePort
                        .save(customerAuditMapper.toSnapshot(saved))
                        .thenReturn(saved))
                .doOnNext(customer -> log.info(Messages.END + "updateCustomer(identification:{})",
                        customer.getIdentification()));
    }

    @Override
    public Mono<Customer> findCustomerByIdentification(String identification) {
        log.info(Messages.START + "findCustomerByIdentification(identification:{})", identification);
        return customerPersistencePort.findActiveCustomerByIdentification(identification)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(identification)))
                .doOnNext(customer -> log.info(Messages.END + "findCustomerByIdentification(identification:{})",
                        customer.getIdentification()));
    }

    @Override
    public Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id) {
        log.info(Messages.START + "findActiveCustomerIdentificationAndFullNameById(id:{})", id);
        return customerPersistencePort.findActiveCustomerIdentificationAndFullNameById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                .doOnNext(resolved -> log.info(
                        Messages.END + "findActiveCustomerIdentificationAndFullNameById(id:{})",
                        id));
    }

    @Override
    public Mono<Void> deleteCustomer(Integer id) {
        log.info(Messages.START + "deleteCustomer(id:{})", id);
        return customerPersistencePort.findActiveCustomerById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                .flatMap(customer -> accountRestrictionPort.existsActiveAccountsForCustomer(id)
                        .flatMap(hasActive -> Boolean.TRUE.equals(hasActive)
                                ? Mono.<Customer>error(new ActiveAccountException())
                                : Mono.just(customer)))
                .flatMap(customer -> {
                    customer.deactivate();
                    return customerPersistencePort.save(customer);
                })
                .flatMap(saved -> customerAuditPersistencePort
                        .save(customerAuditMapper.toSnapshot(saved))
                        .then())
                .doOnSuccess(ignored -> log.info(Messages.END + "deleteCustomer(id:{})", id));
    }

}
