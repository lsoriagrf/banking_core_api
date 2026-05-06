package com.lisdev.transactionalapi.infrastructure.persistence.mapper;

import com.lisdev.transactionalapi.domain.model.Account;
import com.lisdev.transactionalapi.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountPersistenceMapper {

    @Mapping(
            target = "accountType",
            expression =
                    "java(com.lisdev.transactionalapi.domain.model.AccountType.descriptionById(entity.getAccountTypeId()))")
    Account toDomain(AccountEntity entity);

    @BeanMapping(ignoreUnmappedSourceProperties = "accountType")
    AccountEntity toEntity(Account account);
}
