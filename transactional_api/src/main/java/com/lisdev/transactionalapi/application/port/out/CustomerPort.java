package com.lisdev.transactionalapi.application.port.out;

import com.lisdev.transactionalapi.domain.model.CustomerIdentityOutcome;
import reactor.core.publisher.Mono;

public interface CustomerPort {

    /**
     * {@code GET /api/v1/customers/:identification}; uses {@code id} from the customer body.
     */
    Mono<Integer> findIdByIdentification(String identification);

    /**
     * {@code GET /api/v1/customers/:customerId}. Returns {@link CustomerIdentityOutcome#identification()} and
     * {@link CustomerIdentityOutcome#fullName()} from the body;
     */
    Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId);
}
