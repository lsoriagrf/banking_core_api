package com.lisdev.customers_api.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customers_api.domain.model.Customer;
import com.lisdev.customers_api.infrastructure.persistence.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer customer);

}
