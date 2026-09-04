package com.lisdev.customer.infrastructure.persistence.adapter;

import com.lisdev.customer.application.port.out.AccountProjectionPort;
import com.lisdev.customer.common.PersistenceAdapter;
import com.lisdev.customer.domain.event.AccountEvent;
import com.lisdev.customer.infrastructure.persistence.repository.AccountProjectionRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountProjectionAdapter implements AccountProjectionPort {

    private final AccountProjectionRepository accountProjectionRepository;

    @Override
    public Mono<Void> apply(AccountEvent event) {
        AccountEvent.Payload payload = event.payload();
        return accountProjectionRepository.upsert(
                        payload.accountId(),
                        payload.customerId(),
                        payload.accountNumber(),
                        payload.status(),
                        event.eventId(),
                        LocalDateTime.ofInstant(event.occurredAt(), ZoneOffset.UTC))
                .then();
    }

}
