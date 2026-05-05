package com.lisdev.customers_api.application.port.out;

import com.lisdev.customers_api.domain.model.ResolvedActiveCustomer;
import com.lisdev.customers_api.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPersistencePort {

    Mono<Customer> findActiveCustomerByIdentification(String identification);

    Mono<Customer> findDeletedCustomerByIdentification(String identification);

    Mono<Customer> findActiveCustomerById(int id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

    Mono<Customer> save(Customer customer);

}
