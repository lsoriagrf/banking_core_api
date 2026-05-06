package com.lisdev.customersapi.domain.model;

public record ResolvedActiveCustomer(String identification, String fullName) {

    public static ResolvedActiveCustomer fromActive(
        String identification, String firstName, String lastName) {
        String id = identification == null ? "" : identification.trim();
        String fn = firstName == null ? "" : firstName.trim();
        String ln = lastName == null ? "" : lastName.trim();
        return new ResolvedActiveCustomer(id, (fn + " " + ln).trim());
    }
}
