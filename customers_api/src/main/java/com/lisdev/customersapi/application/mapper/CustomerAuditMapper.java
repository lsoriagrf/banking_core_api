package com.lisdev.customersapi.application.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customersapi.domain.model.Customer;
import com.lisdev.customersapi.domain.model.CustomerAuditSnapshot;

@Mapper(componentModel = "spring")
public interface CustomerAuditMapper {

    CustomerAuditSnapshot toSnapshot(Customer customer);

}
