package com.lisdev.customersapi.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateCustomer extends Person {

    @NotBlank(message = "The client password is required")
    private String password;

}
