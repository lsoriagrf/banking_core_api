package com.lisdev.customer.application.port.out;

import reactor.core.publisher.Mono;

public interface AccountRestrictionPort {

    Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId);

}
