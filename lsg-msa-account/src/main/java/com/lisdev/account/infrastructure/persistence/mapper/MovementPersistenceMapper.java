package com.lisdev.account.infrastructure.persistence.mapper;

import com.lisdev.account.domain.model.Movement;
import com.lisdev.account.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementPersistenceMapper {

    default Movement toDomain(MovementEntity entity) {
        return Movement.rehydrate(
                entity.getId(),
                entity.getAccountId(),
                entity.getTransactionTypeId(),
                entity.getTransactionCode(),
                entity.getAmount(),
                entity.getBalance(),
                entity.getNote(),
                entity.getCreatedAt(),
                entity.getCreatedBy());
    }

    MovementEntity toEntity(Movement movement);
}
