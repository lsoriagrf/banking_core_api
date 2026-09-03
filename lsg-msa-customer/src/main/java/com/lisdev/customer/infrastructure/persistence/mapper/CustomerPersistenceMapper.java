package com.lisdev.customer.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.lisdev.customer.domain.model.Customer;
import com.lisdev.customer.infrastructure.persistence.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    default Customer toDomain(CustomerEntity entity) {
        return Customer.rehydrate(
                entity.getId(),
                entity.getIdentification(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPassword(),
                entity.getGender(),
                entity.getBirthdate(),
                entity.getAddress(),
                entity.getPhoneNumber(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }

    CustomerEntity toEntity(Customer customer);

}
