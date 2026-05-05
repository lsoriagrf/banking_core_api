package com.lisdev.customers_api.application.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customers_api.domain.model.Customer;
import com.lisdev.customers_api.domain.model.CustomerAuditSnapshot;

@Mapper(componentModel = "spring")
public interface CustomerAuditMapper {

    CustomerAuditSnapshot toSnapshot(Customer customer);

}
