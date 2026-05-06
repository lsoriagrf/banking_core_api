package com.lisdev.customersapi.application.port.out;

import reactor.core.publisher.Mono;

public interface AccountRestrictionPort {

    Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId);

}
