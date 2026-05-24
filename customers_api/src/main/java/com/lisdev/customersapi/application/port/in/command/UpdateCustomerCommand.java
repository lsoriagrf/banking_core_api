package com.lisdev.customersapi.application.port.in.command;

import java.time.LocalDate;

public record UpdateCustomerCommand(
        String identification,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthdate,
        String address,
        String phoneNumber
) {}
