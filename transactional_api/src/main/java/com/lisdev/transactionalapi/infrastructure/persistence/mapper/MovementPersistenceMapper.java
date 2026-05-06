package com.lisdev.transactionalapi.infrastructure.persistence.mapper;

import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementPersistenceMapper {

    Movement toDomain(MovementEntity entity);

    MovementEntity toEntity(Movement movement);
}
