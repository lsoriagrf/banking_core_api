package com.lisdev.account.application.port.out;

import com.lisdev.account.domain.model.Movement;
import java.time.LocalDate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementPersistencePort {

    Mono<Movement> save(Movement movement);

    Flux<Movement> findByCustomerIdAndCreatedAtBetween(Integer customerId, LocalDate startDate, LocalDate endDate);
}
