package com.lisdev.customersapi.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customersapi.domain.model.Customer;
import com.lisdev.customersapi.infrastructure.persistence.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer customer);

}
