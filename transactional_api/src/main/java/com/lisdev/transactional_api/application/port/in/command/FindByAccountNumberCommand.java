package com.lisdev.transactional_api.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindByAccountNumberCommand {

    private String identification;
    private String accountNumber;

}
