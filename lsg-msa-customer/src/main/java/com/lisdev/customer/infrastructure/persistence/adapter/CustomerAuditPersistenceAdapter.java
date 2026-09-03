package com.lisdev.customer.infrastructure.persistence.adapter;

import com.lisdev.customer.application.port.out.CustomerAuditPersistencePort;
import com.lisdev.customer.common.PersistenceAdapter;
import com.lisdev.customer.domain.model.CustomerAuditSnapshot;
import com.lisdev.customer.infrastructure.persistence.mapper.CustomerAuditPersistenceMapper;
import com.lisdev.customer.infrastructure.persistence.repository.CustomerAuditRepository;
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
