package com.lisdev.customers_api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public Integer getAge() {
        if (birthdate == null) {
            return null;
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

}
