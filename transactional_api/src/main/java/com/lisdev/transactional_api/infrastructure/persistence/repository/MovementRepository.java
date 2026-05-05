package com.lisdev.transactional_api.infrastructure.persistence.repository;

import com.lisdev.transactional_api.infrastructure.persistence.entity.MovementEntity;
import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<MovementEntity, Integer> {

    @Query(
            """
            SELECT m.* FROM movements m
            INNER JOIN account a ON m.account_id = a.id
            WHERE a.status = true
              AND a.customer_id = :customerId
              AND m.created_at::date BETWEEN :startDate AND :endDate
            """)
    Flux<MovementEntity> findByCustomerIdAndCreatedAtBetween(
            @Param("customerId") Integer customerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
