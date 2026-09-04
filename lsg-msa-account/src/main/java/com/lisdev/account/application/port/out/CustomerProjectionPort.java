package com.lisdev.account.application.port.out;

import com.lisdev.account.domain.event.CustomerEvent;
import reactor.core.publisher.Mono;

public interface CustomerProjectionPort {

    Mono<Void> apply(CustomerEvent event);

}
