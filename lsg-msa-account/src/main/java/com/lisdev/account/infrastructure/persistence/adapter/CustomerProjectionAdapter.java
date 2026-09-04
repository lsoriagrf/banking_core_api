package com.lisdev.account.infrastructure.persistence.adapter;

import com.lisdev.account.application.port.out.CustomerProjectionPort;
import com.lisdev.account.common.PersistenceAdapter;
import com.lisdev.account.domain.event.CustomerEvent;
import com.lisdev.account.infrastructure.persistence.repository.CustomerProjectionRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class CustomerProjectionAdapter implements CustomerProjectionPort {

    private final CustomerProjectionRepository customerProjectionRepository;

    @Override
    public Mono<Void> apply(CustomerEvent event) {
        CustomerEvent.Payload payload = event.payload();
        return customerProjectionRepository.upsert(
                        payload.customerId(),
                        payload.identification(),
                        payload.fullName(),
                        payload.status(),
                        event.eventId(),
                        LocalDateTime.ofInstant(event.occurredAt(), ZoneOffset.UTC))
                .then();
    }

}
