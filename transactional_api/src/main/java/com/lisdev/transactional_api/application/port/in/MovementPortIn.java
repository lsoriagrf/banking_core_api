package com.lisdev.transactional_api.application.port.in;

import com.lisdev.transactional_api.application.port.in.command.TransactionCommand;
import com.lisdev.transactional_api.domain.model.Movement;
import reactor.core.publisher.Mono;

public interface MovementPortIn {

    Mono<Movement> withdrawal(TransactionCommand body);

    Mono<Movement> deposit(TransactionCommand body);

}
