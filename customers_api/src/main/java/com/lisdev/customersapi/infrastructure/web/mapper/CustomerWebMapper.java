package com.lisdev.customersapi.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customersapi.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customersapi.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customersapi.domain.model.ResolvedActiveCustomer;
import com.lisdev.customersapi.domain.model.Customer;
import com.lisdev.customersapi.infrastructure.web.dto.request.CreateCustomer;
import com.lisdev.customersapi.infrastructure.web.dto.request.UpdateCustomer;
import com.lisdev.customersapi.infrastructure.web.dto.response.CustomerResolvedResponse;
import com.lisdev.customersapi.infrastructure.web.dto.response.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand toCreateCommand(CreateCustomer dto);

    UpdateCustomerCommand toUpdateCommand(UpdateCustomer dto);

    CustomerResponse toResponse(Customer customer);

    default CustomerResolvedResponse toResolvedResponse(ResolvedActiveCustomer resolved) {
        return new CustomerResolvedResponse(resolved.identification(), resolved.fullName());
    }
}
