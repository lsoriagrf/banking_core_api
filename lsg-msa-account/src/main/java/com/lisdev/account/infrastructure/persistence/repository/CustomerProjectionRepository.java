package com.lisdev.account.infrastructure.persistence.repository;

import com.lisdev.account.infrastructure.persistence.entity.CustomerProjectionEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerProjectionRepository
        extends ReactiveCrudRepository<CustomerProjectionEntity, Integer> {

    Mono<CustomerProjectionEntity> findByIdentificationAndStatusTrue(String identification);

    Mono<CustomerProjectionEntity> findByCustomerIdAndStatusTrue(Integer customerId);

    @Modifying
    @Query("""
            INSERT INTO customer_projection
                (customer_id, identification, full_name, status, last_event_id, last_event_at, updated_at)
            VALUES
                (:customerId, :identification, :fullName, :status, :eventId, :occurredAt, NOW())
            ON CONFLICT (customer_id) DO UPDATE SET
                identification = EXCLUDED.identification,
                full_name      = EXCLUDED.full_name,
                status         = EXCLUDED.status,
                last_event_id  = EXCLUDED.last_event_id,
                last_event_at  = EXCLUDED.last_event_at,
                updated_at     = NOW()
            WHERE customer_projection.last_event_at IS NULL
               OR customer_projection.last_event_at < EXCLUDED.last_event_at
            """)
    Mono<Long> upsert(
            @Param("customerId") Integer customerId,
            @Param("identification") String identification,
            @Param("fullName") String fullName,
            @Param("status") Boolean status,
            @Param("eventId") UUID eventId,
            @Param("occurredAt") LocalDateTime occurredAt);

}
