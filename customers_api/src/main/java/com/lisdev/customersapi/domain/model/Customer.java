package com.lisdev.customersapi.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import lombok.Getter;

@Getter
public class Customer {

    private Integer id;
    private String identification;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private LocalDate birthdate;
    private String address;
    private String phoneNumber;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public static Customer createNew(
            String identification,
            String firstName,
            String lastName,
            String gender,
            LocalDate birthdate,
            String address,
            String phoneNumber) {
        Customer customer = new Customer();
        customer.identification = identification;
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.gender = gender;
        customer.birthdate = birthdate;
        customer.address = address;
        customer.phoneNumber = phoneNumber;
        customer.status = true;
        customer.createdAt = LocalDateTime.now();
        customer.createdBy = identification;
        return customer;
    }

    public static Customer rehydrate(
            Integer id,
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
        customer.identification = identification;
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.password = password;
        customer.gender = gender;
        customer.birthdate = birthdate;
        customer.address = address;
        customer.phoneNumber = phoneNumber;
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
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public Integer getAge() {
        if (birthdate == null) {
            return null;
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

}
