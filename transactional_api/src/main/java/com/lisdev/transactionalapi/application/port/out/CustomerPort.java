package com.lisdev.transactionalapi.application.port.out;

import com.lisdev.transactionalapi.domain.model.CustomerIdentityOutcome;
import reactor.core.publisher.Mono;

public interface CustomerPort {

    /**
     * {@code GET /api/v1/customers?identification=:identification}; response body {@code { "id": n }}.
     */
    Mono<Integer> findIdByIdentification(String identification);

    /**
     * {@code GET /api/v1/customers/resolve?id=:customerId}. Returns {@link CustomerIdentityOutcome#identification()} and
     * {@link CustomerIdentityOutcome#fullName()} from the body;
     */
    Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId);
}
