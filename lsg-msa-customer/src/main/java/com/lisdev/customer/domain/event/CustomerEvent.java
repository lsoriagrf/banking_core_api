package com.lisdev.customer.domain.event;

import com.lisdev.customer.domain.model.Customer;
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

    public static CustomerEvent from(CustomerEventType eventType, Customer customer) {
        Payload payload = new Payload(
                customer.getId(),
                customer.getIdentification(),
                fullName(customer),
                customer.getStatus());
        return new CustomerEvent(UUID.randomUUID(), eventType, Instant.now(), payload);
    }

    private static String fullName(Customer customer) {
        String firstName = customer.getFirstName() == null ? "" : customer.getFirstName().trim();
        String lastName = customer.getLastName() == null ? "" : customer.getLastName().trim();
        return (firstName + " " + lastName).trim();
    }
}
