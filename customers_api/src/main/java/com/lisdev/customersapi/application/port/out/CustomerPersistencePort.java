package com.lisdev.customersapi.application.port.out;

import com.lisdev.customersapi.domain.model.ResolvedActiveCustomer;
import com.lisdev.customersapi.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPersistencePort {

    Mono<Customer> findActiveCustomerByIdentification(String identification);

    Mono<Customer> findDeletedCustomerByIdentification(String identification);

    Mono<Customer> findActiveCustomerById(int id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

    Mono<Customer> save(Customer customer);

}
