package com.lisdev.customer.domain.event;

import java.time.Instant;
import java.util.UUID;

public record AccountEvent(
        UUID eventId,
        AccountEventType eventType,
        Instant occurredAt,
        Payload payload) {

    public record Payload(
            Integer accountId,
            Integer customerId,
            String accountNumber,
            Boolean status) {
    }
}
