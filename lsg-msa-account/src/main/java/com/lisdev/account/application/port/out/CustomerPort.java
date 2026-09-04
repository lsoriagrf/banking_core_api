package com.lisdev.account.application.port.out;

import com.lisdev.account.domain.model.CustomerIdentityOutcome;
import reactor.core.publisher.Mono;

public interface CustomerPort {

    Mono<Integer> findIdByIdentification(String identification);

    Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId);
}
