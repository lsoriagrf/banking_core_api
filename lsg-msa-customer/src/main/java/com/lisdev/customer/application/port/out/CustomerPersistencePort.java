package com.lisdev.customer.application.port.out;

import com.lisdev.customer.domain.model.ResolvedActiveCustomer;
import com.lisdev.customer.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPersistencePort {

    Mono<Customer> findActiveCustomerByIdentification(String identification);

    Mono<Customer> findDeletedCustomerByIdentification(String identification);

    Mono<Customer> findActiveCustomerById(int id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

    Mono<Customer> save(Customer customer);

}
