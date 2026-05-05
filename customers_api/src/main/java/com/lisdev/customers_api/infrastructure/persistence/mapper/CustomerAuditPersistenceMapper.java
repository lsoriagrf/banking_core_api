package com.lisdev.customers_api.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.lisdev.customers_api.domain.model.CustomerAuditSnapshot;
import com.lisdev.customers_api.infrastructure.persistence.entity.CustomerAuditEntity;

@Mapper(componentModel = "spring")
public interface CustomerAuditPersistenceMapper {

    @Mapping(target = "auditId", ignore = true)
    CustomerAuditEntity toEntity(CustomerAuditSnapshot snapshot);

}
