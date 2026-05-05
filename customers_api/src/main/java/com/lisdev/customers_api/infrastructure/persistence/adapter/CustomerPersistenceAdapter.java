package com.lisdev.customers_api.infrastructure.persistence.adapter;

import com.lisdev.customers_api.application.port.out.CustomerPersistencePort;
import com.lisdev.customers_api.common.PersistenceAdapter;
import com.lisdev.customers_api.domain.model.ResolvedActiveCustomer;
import com.lisdev.customers_api.domain.model.Customer;
import com.lisdev.customers_api.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import com.lisdev.customers_api.infrastructure.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistencePort {

    private final CustomerRepository customerRepository;
    private final CustomerPersistenceMapper persistenceMapper;

    @Override
    public Mono<Customer> findActiveCustomerByIdentification(String identification) {
        return customerRepository.findByIdentificationAndStatusTrue(identification)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Mono<Customer> findDeletedCustomerByIdentification(String identification) {
        return customerRepository.findByIdentificationAndStatusFalse(identification)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Mono<Customer> findActiveCustomerById(int id) {
        return customerRepository.findByIdAndStatusTrue(id)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id) {
        return customerRepository.findByIdAndStatusTrue(id)
                .map(entity -> ResolvedActiveCustomer.fromActive(
                        entity.getIdentification(),
                        entity.getFirstName(),
                        entity.getLastName()));
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(persistenceMapper.toEntity(customer))
                .map(persistenceMapper::toDomain);
    }

}
