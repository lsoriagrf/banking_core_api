package com.lisdev.customers_api.infrastructure.web.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomerResponse {

    private Integer id;
    private String identification;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phoneNumber;
    private Integer age;

}
