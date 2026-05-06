package com.lisdev.transactionalapi.application.port.in.command;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionCommand {

    private String identification;
    private String accountNumber;
    private BigDecimal amount;

}
