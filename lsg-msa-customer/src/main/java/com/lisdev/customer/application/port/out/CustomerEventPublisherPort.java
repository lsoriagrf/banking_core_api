package com.lisdev.customer.application.port.out;

import com.lisdev.customer.domain.event.CustomerEvent;
import reactor.core.publisher.Mono;

public interface CustomerEventPublisherPort {

    Mono<Void> publish(CustomerEvent event);

}
