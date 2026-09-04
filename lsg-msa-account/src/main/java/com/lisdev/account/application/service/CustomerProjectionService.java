package com.lisdev.account.application.service;

import com.lisdev.account.application.port.in.CustomerEventHandlerPortIn;
import com.lisdev.account.application.port.out.CustomerProjectionPort;
import com.lisdev.account.common.UseCase;
import com.lisdev.account.domain.Messages;
import com.lisdev.account.domain.event.CustomerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CustomerProjectionService implements CustomerEventHandlerPortIn {

    private final CustomerProjectionPort customerProjectionPort;

    @Override
    public Mono<Void> handle(CustomerEvent event) {
        log.info(Messages.START + "handleCustomerEvent(eventType:{}, customerId:{})",
                event.eventType(), event.payload().customerId());
        return customerProjectionPort.apply(event)
                .doOnSuccess(ignored -> log.info(Messages.END + "handleCustomerEvent(eventId:{})",
                        event.eventId()));
    }

}
