package com.lisdev.customer.application.port.out;

import com.lisdev.customer.domain.event.AccountEvent;
import reactor.core.publisher.Mono;

public interface AccountProjectionPort {

    Mono<Void> apply(AccountEvent event);

}
