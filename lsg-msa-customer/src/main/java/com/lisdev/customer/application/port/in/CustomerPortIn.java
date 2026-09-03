package com.lisdev.customer.application.port.in;

import com.lisdev.customer.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customer.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customer.domain.model.ResolvedActiveCustomer;
import com.lisdev.customer.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPortIn {

    Mono<Customer> createCustomer(CreateCustomerCommand command);

    Mono<Customer> updateCustomer(UpdateCustomerCommand command);

    Mono<Customer> findCustomerByIdentification(String identification);

    Mono<Void> deleteCustomer(Integer id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

}
