package com.lisdev.customers_api.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.lisdev.customers_api.application.port.in.command.CreateCustomerCommand;
import com.lisdev.customers_api.application.port.in.command.UpdateCustomerCommand;
import com.lisdev.customers_api.domain.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "status", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "identification")
    Customer toCustomer(CreateCustomerCommand command);

    @Mapping(target = "updatedBy", source = "identification")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntity(UpdateCustomerCommand command, @MappingTarget Customer customer);

    @Mapping(target = "status", constant = "true")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", source = "identification")
    void restoreEntity(CreateCustomerCommand command, @MappingTarget Customer customer);

}
