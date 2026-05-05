package com.lisdev.transactional_api.application.port.in.command;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateAccountCommand {

    private String identification;
    private Integer accountTypeId;
    private BigDecimal balance;

}
