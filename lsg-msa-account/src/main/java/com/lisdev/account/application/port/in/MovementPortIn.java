package com.lisdev.account.application.port.in;

import com.lisdev.account.application.port.in.command.TransactionCommand;
import com.lisdev.account.domain.model.Movement;
import reactor.core.publisher.Mono;

public interface MovementPortIn {

    Mono<Movement> withdrawal(TransactionCommand body);

    Mono<Movement> deposit(TransactionCommand body);

}
