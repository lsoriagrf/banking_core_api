package com.lisdev.customer.infrastructure.persistence.adapter;

import com.lisdev.customer.application.port.out.AccountRestrictionPort;
import com.lisdev.customer.common.PersistenceAdapter;
import com.lisdev.customer.infrastructure.persistence.repository.AccountProjectionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountRestrictionAdapter implements AccountRestrictionPort {

    private final AccountProjectionRepository accountProjectionRepository;

    @Override
    public Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId) {
        return accountProjectionRepository.existsByCustomerIdAndStatusTrue(customerId)
                .defaultIfEmpty(false);
    }

}
