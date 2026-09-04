package com.lisdev.customer.application.service;

import com.lisdev.customer.application.port.in.AccountEventHandlerPortIn;
import com.lisdev.customer.application.port.out.AccountProjectionPort;
import com.lisdev.customer.common.UseCase;
import com.lisdev.customer.domain.Messages;
import com.lisdev.customer.domain.event.AccountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class AccountProjectionService implements AccountEventHandlerPortIn {

    private final AccountProjectionPort accountProjectionPort;

    @Override
    public Mono<Void> handle(AccountEvent event) {
        log.info(Messages.START + "handleAccountEvent(eventType:{}, accountId:{})",
                event.eventType(), event.payload().accountId());
        return accountProjectionPort.apply(event)
                .doOnSuccess(ignored -> log.info(Messages.END + "handleAccountEvent(eventId:{})",
                        event.eventId()));
    }

}
