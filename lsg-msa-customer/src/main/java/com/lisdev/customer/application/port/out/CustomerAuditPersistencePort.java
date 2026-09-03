package com.lisdev.customer.application.port.out;

import com.lisdev.customer.domain.model.CustomerAuditSnapshot;
import reactor.core.publisher.Mono;

public interface CustomerAuditPersistencePort {

    Mono<Void> save(CustomerAuditSnapshot snapshot);

}
