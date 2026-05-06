package com.lisdev.customersapi.application.port.in;

import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.domain.model.ResolvedActiveCustomer;
import com.lisdev.customersapi.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerPortIn {

    Mono<Customer> createCustomer(CreateCustomerCommand command);

    Mono<Customer> updateCustomer(UpdateCustomerCommand command);

    Mono<Customer> findCustomerByIdentification(String identification);

    Mono<Integer> findIdByIdentification(String identification);

    Mono<Void> deleteCustomer(Integer id);

    Mono<ResolvedActiveCustomer> findActiveCustomerIdentificationAndFullNameById(int id);

}
