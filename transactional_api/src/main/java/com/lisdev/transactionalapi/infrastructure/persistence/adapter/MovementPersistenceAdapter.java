package com.lisdev.transactionalapi.infrastructure.persistence.adapter;

import com.lisdev.transactionalapi.application.port.out.MovementPersistencePort;
import com.lisdev.transactionalapi.common.PersistenceAdapter;
import com.lisdev.transactionalapi.domain.model.Movement;
import com.lisdev.transactionalapi.infrastructure.persistence.mapper.MovementPersistenceMapper;
import com.lisdev.transactionalapi.infrastructure.persistence.repository.MovementRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class MovementPersistenceAdapter implements MovementPersistencePort {

    private final MovementRepository movementRepository;
    private final MovementPersistenceMapper movementPersistenceMapper;

    @Override
    public Mono<Movement> save(Movement movement) {
        return movementRepository
                .save(movementPersistenceMapper.toEntity(movement))
                .map(movementPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Movement> findByCustomerIdAndCreatedAtBetween(
            Integer customerId, LocalDate startDate, LocalDate endDate) {
        return movementRepository
                .findByCustomerIdAndCreatedAtBetween(customerId, startDate, endDate)
                .map(movementPersistenceMapper::toDomain);
    }
}
