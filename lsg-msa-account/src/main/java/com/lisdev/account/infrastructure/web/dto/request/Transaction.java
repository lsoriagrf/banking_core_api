package com.lisdev.account.infrastructure.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transaction {

    @NotBlank(message = "Identification is required")
    @Size(max = 15, message = "Identification must not exceed 15 characters")
    private String identification;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{6}", message = "Account number must be 6 digits")
    private String accountNumber;

    @NotNull(message = "The amount is required")
    @Positive(message = "Amount must be positive")
    @Digits(integer = 12, fraction = 2, message = "Only up to two decimal places are allowed")
    private BigDecimal amount;

}
