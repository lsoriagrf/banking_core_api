package com.lisdev.customersapi.infrastructure.persistence.adapter;

import com.lisdev.customersapi.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customersapi.common.PersistenceAdapter;
import com.lisdev.customersapi.domain.model.CustomerAuditSnapshot;
import com.lisdev.customersapi.infrastructure.persistence.mapper.CustomerAuditPersistenceMapper;
import com.lisdev.customersapi.infrastructure.persistence.repository.CustomerAuditRepository;
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
