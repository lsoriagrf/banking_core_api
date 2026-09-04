package com.lisdev.customer.infrastructure.persistence.repository;

import com.lisdev.customer.infrastructure.persistence.entity.AccountProjectionEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountProjectionRepository
        extends ReactiveCrudRepository<AccountProjectionEntity, Integer> {

    Mono<Boolean> existsByCustomerIdAndStatusTrue(Integer customerId);

    @Modifying
    @Query("""
            INSERT INTO account_projection
                (account_id, customer_id, account_number, status, last_event_id, last_event_at, updated_at)
            VALUES
                (:accountId, :customerId, :accountNumber, :status, :eventId, :occurredAt, NOW())
            ON CONFLICT (account_id) DO UPDATE SET
                customer_id    = EXCLUDED.customer_id,
                account_number = EXCLUDED.account_number,
                status         = EXCLUDED.status,
                last_event_id  = EXCLUDED.last_event_id,
                last_event_at  = EXCLUDED.last_event_at,
                updated_at     = NOW()
            WHERE account_projection.last_event_at IS NULL
               OR account_projection.last_event_at < EXCLUDED.last_event_at
            """)
    Mono<Long> upsert(
            @Param("accountId") Integer accountId,
            @Param("customerId") Integer customerId,
            @Param("accountNumber") String accountNumber,
            @Param("status") Boolean status,
            @Param("eventId") UUID eventId,
            @Param("occurredAt") LocalDateTime occurredAt);

}
