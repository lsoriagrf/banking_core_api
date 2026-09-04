package com.lisdev.customer.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customer.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customer.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customer.domain.model.Customer;
import com.lisdev.customer.infrastructure.web.dto.request.CreateCustomer;
import com.lisdev.customer.infrastructure.web.dto.request.Person;
import com.lisdev.customer.infrastructure.web.dto.response.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand toCreateCommand(CreateCustomer dto);

    UpdateCustomerCommand toUpdateCommand(Integer id, Person dto);

    CustomerResponse toResponse(Customer customer);
}
