package com.lisdev.customer.application.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customer.domain.model.Customer;
import com.lisdev.customer.domain.model.CustomerAuditSnapshot;

@Mapper(componentModel = "spring")
public interface CustomerAuditMapper {

    CustomerAuditSnapshot toSnapshot(Customer customer);

}
