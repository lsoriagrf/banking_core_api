package com.lisdev.customersapi.application.port.out;

import com.lisdev.customersapi.domain.model.CustomerAuditSnapshot;
import reactor.core.publisher.Mono;

public interface CustomerAuditPersistencePort {

    Mono<Void> save(CustomerAuditSnapshot snapshot);

}
