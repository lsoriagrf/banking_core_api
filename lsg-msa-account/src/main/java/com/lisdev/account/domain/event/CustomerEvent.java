package com.lisdev.account.domain.event;

import java.time.Instant;
import java.util.UUID;

public record CustomerEvent(
        UUID eventId,
        CustomerEventType eventType,
        Instant occurredAt,
        Payload payload) {

    public record Payload(
            Integer customerId,
            String identification,
            String fullName,
            Boolean status) {
    }
}
