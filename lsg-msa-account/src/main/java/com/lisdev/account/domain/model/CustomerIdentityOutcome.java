package com.lisdev.account.domain.model;

public record CustomerIdentityOutcome(String identification, String fullName) {

    public boolean isPresent() {
        return identification != null && !identification.isBlank();
    }
}
