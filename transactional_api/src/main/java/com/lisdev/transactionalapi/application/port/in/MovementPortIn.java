package com.lisdev.transactionalapi.application.port.in;

import com.lisdev.transactionalapi.application.port.in.command.TransactionCommand;
import com.lisdev.transactionalapi.domain.model.Movement;
import reactor.core.publisher.Mono;

public interface MovementPortIn {

    Mono<Movement> withdrawal(TransactionCommand body);

    Mono<Movement> deposit(TransactionCommand body);

}
