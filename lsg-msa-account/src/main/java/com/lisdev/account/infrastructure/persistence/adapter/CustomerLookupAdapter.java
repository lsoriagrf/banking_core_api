package com.lisdev.account.infrastructure.persistence.adapter;

import com.lisdev.account.application.port.out.CustomerPort;
import com.lisdev.account.common.PersistenceAdapter;
import com.lisdev.account.domain.model.CustomerIdentityOutcome;
import com.lisdev.account.infrastructure.persistence.entity.CustomerProjectionEntity;
import com.lisdev.account.infrastructure.persistence.repository.CustomerProjectionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerLookupAdapter implements CustomerPort {

    private static final CustomerIdentityOutcome NOT_FOUND = new CustomerIdentityOutcome("", "");

    private final CustomerProjectionRepository customerProjectionRepository;

    @Override
    public Mono<Integer> findIdByIdentification(String identification) {
        return customerProjectionRepository.findByIdentificationAndStatusTrue(identification)
                .map(CustomerProjectionEntity::getCustomerId);
    }

    @Override
    public Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId) {
        return customerProjectionRepository.findByCustomerIdAndStatusTrue(customerId)
                .map(entity -> new CustomerIdentityOutcome(
                        entity.getIdentification(), entity.getFullName()))
                .defaultIfEmpty(NOT_FOUND);
    }

}
