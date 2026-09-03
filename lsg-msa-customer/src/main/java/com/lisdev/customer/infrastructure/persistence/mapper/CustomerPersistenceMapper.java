package com.lisdev.customer.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.lisdev.customer.domain.model.Customer;
import com.lisdev.customer.infrastructure.persistence.entity.CustomerEntity;
import com.lisdev.customer.infrastructure.persistence.entity.PersonEntity;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    default Customer toDomain(CustomerEntity customerEntity, PersonEntity personEntity) {
        return Customer.rehydrate(
                customerEntity.getId(),
                personEntity.getId(),
                personEntity.getIdentification(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                customerEntity.getPassword(),
                personEntity.getGender(),
                personEntity.getBirthdate(),
                personEntity.getAddress(),
                personEntity.getPhoneNumber(),
                customerEntity.getStatus(),
                customerEntity.getCreatedAt(),
                customerEntity.getUpdatedAt(),
                customerEntity.getCreatedBy(),
                customerEntity.getUpdatedBy());
    }

    CustomerEntity toCustomerEntity(Customer customer);

    @Mapping(target = "id", source = "personId")
    PersonEntity toPersonEntity(Customer customer);

}
