package com.lisdev.account.application.port.in;

import com.lisdev.account.domain.event.CustomerEvent;
import reactor.core.publisher.Mono;

public interface CustomerEventHandlerPortIn {

    Mono<Void> handle(CustomerEvent event);

}
