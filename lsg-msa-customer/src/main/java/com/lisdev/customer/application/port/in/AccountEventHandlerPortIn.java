package com.lisdev.customer.application.port.in;

import com.lisdev.customer.domain.event.AccountEvent;
import reactor.core.publisher.Mono;

public interface AccountEventHandlerPortIn {

    Mono<Void> handle(AccountEvent event);

}
