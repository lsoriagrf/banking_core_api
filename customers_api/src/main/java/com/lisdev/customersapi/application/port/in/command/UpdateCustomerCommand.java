package com.lisdev.customersapi.application.port.in.command;

import java.time.LocalDate;

public record UpdateCustomerCommand(
        Integer id,
        String identification,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthdate,
        String address,
        String phoneNumber
) {}
