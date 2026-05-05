package com.lisdev.transactional_api.infrastructure.persistence.mapper;

import com.lisdev.transactional_api.domain.model.Movement;
import com.lisdev.transactional_api.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementPersistenceMapper {

    Movement toDomain(MovementEntity entity);

    MovementEntity toEntity(Movement movement);
}
