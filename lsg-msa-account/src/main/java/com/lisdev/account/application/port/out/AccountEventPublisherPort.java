package com.lisdev.account.application.port.out;

import com.lisdev.account.domain.event.AccountEvent;
import reactor.core.publisher.Mono;

public interface AccountEventPublisherPort {

    Mono<Void> publish(AccountEvent event);

}
