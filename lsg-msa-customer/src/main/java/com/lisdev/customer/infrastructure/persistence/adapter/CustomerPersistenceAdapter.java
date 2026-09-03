package com.lisdev.customer.infrastructure.persistence.adapter;

import com.lisdev.customer.application.port.out.CustomerPersistencePort;
import com.lisdev.customer.common.PersistenceAdapter;
import com.lisdev.customer.domain.model.ResolvedActiveCustomer;
import com.lisdev.customer.domain.model.Customer;
import com.lisdev.customer.infrastructure.persistence.entity.CustomerEntity;
import com.lisdev.customer.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import com.lisdev.customer.infrastructure.persistence.repository.CustomerRepository;
import com.lisdev.customer.infrastructure.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistencePort {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CustomerPersistenceMapper persistenceMapper;

    @Override
    public Mono<Customer> findActiveCustomerByIdentification(String identification) {
        return personRepository.findByIdentification(identification)
                .flatMap(person -> customerRepository.findByPersonIdAndStatusTrue(person.getId())
                        .map(customer -> persistenceMapper.toDomain(customer, person)));
    }

    @Override
    public Mono<Customer> findDeletedCustomerByIdentification(String identification) {
        return personRepository.findByIdentification(identification)
                .flatMap(person -> customerRepository.findByPersonIdAndStatusFalse(person.getId())
                        .map(customer -> persistenceMapper.toDomain(customer, person)));
    }

    @Override
    public Mono<Customer> findActiveCustomerById(int id) {
        return customerRepository.findByIdAndStatusTrue(id)
                .flatMap(customer -> personRepository.findById(customer.getPersonId())
                        .map(person -> persistenceMapper.toDomain(customer, person)));
    }

    @Override
    public Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id) {
        return customerRepository.findByIdAndStatusTrue(id)
                .flatMap(customer -> personRepository.findById(customer.getPersonId()))
                .map(person -> ResolvedActiveCustomer.fromActive(
                        person.getIdentification(),
                        person.getFirstName(),
                        person.getLastName()));
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return personRepository.save(persistenceMapper.toPersonEntity(customer))
                .flatMap(person -> {
                    CustomerEntity customerEntity = persistenceMapper.toCustomerEntity(customer);
                    customerEntity.setPersonId(person.getId());
                    return customerRepository.save(customerEntity)
                            .map(saved -> persistenceMapper.toDomain(saved, person));
                });
    }

}
