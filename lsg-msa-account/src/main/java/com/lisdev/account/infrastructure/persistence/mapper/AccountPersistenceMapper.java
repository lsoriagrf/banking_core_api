package com.lisdev.account.infrastructure.persistence.mapper;

import com.lisdev.account.domain.model.Account;
import com.lisdev.account.domain.model.AccountType;
import com.lisdev.account.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountPersistenceMapper {

    default Account toDomain(AccountEntity entity) {
        return Account.rehydrate(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getCustomerId(),
                entity.getAccountTypeId(),
                AccountType.descriptionById(entity.getAccountTypeId()),
                entity.getBalance(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }

    @BeanMapping(ignoreUnmappedSourceProperties = "accountType")
    AccountEntity toEntity(Account account);
}
