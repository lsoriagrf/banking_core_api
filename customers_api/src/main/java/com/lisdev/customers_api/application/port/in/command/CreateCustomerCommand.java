package com.lisdev.customers_api.application.port.in.command;

import java.time.LocalDate;

public record CreateCustomerCommand(
        String identification,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthdate,
        String address,
        String phoneNumber,
        String password
) {}
