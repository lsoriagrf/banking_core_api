package com.lisdev.customersapi.application.mapper;

import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CreateCustomerCommand command) {
        return Customer.createNew(
                command.identification(),
                command.firstName(),
                command.lastName(),
                command.gender(),
                command.birthdate(),
                command.address(),
                command.phoneNumber());
    }

    public void updateEntity(UpdateCustomerCommand command, Customer customer) {
        customer.update(
                command.identification(),
                command.firstName(),
                command.lastName(),
                command.gender(),
                command.birthdate(),
                command.address(),
                command.phoneNumber());
    }

    public void restoreEntity(CreateCustomerCommand command, Customer customer) {
        customer.restore(
                command.identification(),
                command.firstName(),
                command.lastName(),
                command.gender(),
                command.birthdate(),
                command.address(),
                command.phoneNumber());
    }
}
