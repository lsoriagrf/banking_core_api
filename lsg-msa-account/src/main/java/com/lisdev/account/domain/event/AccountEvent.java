package com.lisdev.account.domain.event;

import com.lisdev.account.domain.model.Account;
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

    public static AccountEvent from(AccountEventType eventType, Account account) {
        Payload payload = new Payload(
                account.getId(),
                account.getCustomerId(),
                account.getAccountNumber(),
                account.getStatus());
        return new AccountEvent(UUID.randomUUID(), eventType, Instant.now(), payload);
    }
}
