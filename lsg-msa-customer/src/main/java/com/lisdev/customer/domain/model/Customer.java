package com.lisdev.customer.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Customer extends Person {

    private Integer id;
    private String password;
    private Boolean status;

    private Customer() {}

    public static Customer createNew(
            String identification,
            String firstName,
            String lastName,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber) {
        Customer customer = new Customer();
        customer.assignPersonalData(
                identification, firstName, lastName, gender, birthdate, address, phoneNumber);
        customer.status = true;
        customer.createdAt = LocalDateTime.now();
        customer.createdBy = identification;
        return customer;
    }

    public static Customer rehydrate(
            Integer id,
            Integer personId,
            String identification,
            String firstName,
            String lastName,
            String password,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber,
            Boolean status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy) {
        Customer customer = new Customer();
        customer.id = id;
        customer.personId = personId;
        customer.assignPersonalData(
                identification, firstName, lastName, gender, birthdate, address, phoneNumber);
        customer.password = password;
        customer.status = status;
        customer.createdAt = createdAt;
        customer.updatedAt = updatedAt;
        customer.createdBy = createdBy;
        customer.updatedBy = updatedBy;
        return customer;
    }

    public void restore(
            String identification,
            String firstName,
            String lastName,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber) {
        assignPersonalData(
                identification, firstName, lastName, gender, birthdate, address, phoneNumber);
        this.status = true;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = identification;
    }

    public void update(
            String identification,
            String firstName,
            String lastName,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber) {
        assignPersonalData(
                identification, firstName, lastName, gender, birthdate, address, phoneNumber);
        this.updatedBy = identification;
        this.updatedAt = LocalDateTime.now();
    }

    public void assignEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void deactivate() {
        this.status = false;
        this.updatedBy = this.createdBy;
        this.updatedAt = LocalDateTime.now();
    }

}
