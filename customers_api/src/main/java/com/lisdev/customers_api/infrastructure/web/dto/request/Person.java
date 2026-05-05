package com.lisdev.customers_api.infrastructure.web.dto.request;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Person {

    @NotBlank(message = "Client identification is required")
    @Size(max = 15, message = "Identification must not exceed 15 characters")
    private String identification;

    @NotBlank(message = "Client first name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Client last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Client gender is required")
    @Pattern(regexp = "M|F", message = "Gender must be 'M' or 'F'")
    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be before the current date")
    private LocalDate birthdate;

    @NotBlank(message = "Client address is required")
    private String address;

    @NotBlank(message = "Client phone number is required")
    @Size(max = 12, message = "Phone number must not exceed 12 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers")
    private String phoneNumber;

}
