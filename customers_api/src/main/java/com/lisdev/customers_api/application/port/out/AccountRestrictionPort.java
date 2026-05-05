package com.lisdev.customers_api.application.port.out;

import reactor.core.publisher.Mono;

public interface AccountRestrictionPort {

    Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId);

}
