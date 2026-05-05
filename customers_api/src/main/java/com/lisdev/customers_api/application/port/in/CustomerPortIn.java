package com.lisdev.customers_api.application.port.in;

import com.lisdev.customers_api.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customers_api.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customers_api.domain.model.ResolvedActiveCustomer;
import com.lisdev.customers_api.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPortIn {

    Mono<Customer> createCustomer(CreateCustomerCommand command);

    Mono<Customer> updateCustomer(UpdateCustomerCommand command);

    Mono<Customer> findCustomerByIdentification(String identification);

    Mono<Integer> findIdByIdentification(String identification);

    Mono<Void> deleteCustomer(Integer id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

}
