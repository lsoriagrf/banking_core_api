package com.lisdev.customers_api.application.port.out;

import com.lisdev.customers_api.domain.model.CustomerAuditSnapshot;
import reactor.core.publisher.Mono;

public interface CustomerAuditPersistencePort {

    Mono<Void> save(CustomerAuditSnapshot snapshot);

}
