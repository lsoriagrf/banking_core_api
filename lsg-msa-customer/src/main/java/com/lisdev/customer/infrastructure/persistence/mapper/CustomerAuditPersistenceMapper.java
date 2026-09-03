package com.lisdev.customer.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.lisdev.customer.domain.model.CustomerAuditSnapshot;
import com.lisdev.customer.infrastructure.persistence.entity.CustomerAuditEntity;

@Mapper(componentModel = "spring")
public interface CustomerAuditPersistenceMapper {

    @Mapping(target = "auditId", ignore = true)
    CustomerAuditEntity toEntity(CustomerAuditSnapshot snapshot);

}
