package com.lisdev.customers_api.infrastructure.persistence.adapter;

import com.lisdev.customers_api.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customers_api.common.PersistenceAdapter;
import com.lisdev.customers_api.domain.model.CustomerAuditSnapshot;
import com.lisdev.customers_api.infrastructure.persistence.mapper.CustomerAuditPersistenceMapper;
import com.lisdev.customers_api.infrastructure.persistence.repository.CustomerAuditRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerAuditPersistenceAdapter implements CustomerAuditPersistencePort {

    private final CustomerAuditRepository customerAuditRepository;
    private final CustomerAuditPersistenceMapper persistenceMapper;

    @Override
    public Mono<Void> save(CustomerAuditSnapshot snapshot) {
        return customerAuditRepository.save(persistenceMapper.toEntity(snapshot)).then();
    }

}
