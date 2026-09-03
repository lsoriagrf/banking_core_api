package com.lisdev.customer.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import lombok.Getter;

@Getter
public class Person {

    protected Integer personId;
    protected String identification;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected LocalDate birthdate;
    protected String address;
    protected String phoneNumber;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String createdBy;
    protected String updatedBy;

    protected Person() {}

    protected void assignPersonalData(
            String identification,
            String firstName,
            String lastName,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber) {
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        if (birthdate == null) {
            return null;
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

}
