package com.lisdev.customers_api.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customers_api.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customers_api.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customers_api.domain.model.ResolvedActiveCustomer;
import com.lisdev.customers_api.domain.model.Customer;
import com.lisdev.customers_api.infrastructure.web.dto.request.CreateCustomer;
import com.lisdev.customers_api.infrastructure.web.dto.request.UpdateCustomer;
import com.lisdev.customers_api.infrastructure.web.dto.response.CustomerResolvedResponse;
import com.lisdev.customers_api.infrastructure.web.dto.response.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand toCreateCommand(CreateCustomer dto);

    UpdateCustomerCommand toUpdateCommand(UpdateCustomer dto);

    CustomerResponse toResponse(Customer customer);

    default CustomerResolvedResponse toResolvedResponse(ResolvedActiveCustomer resolved) {
        return new CustomerResolvedResponse(resolved.identification(), resolved.fullName());
    }
}
