package com.lisdev.account.application.mapper;

import com.lisdev.account.application.port.in.command.CreateAccountCommand;
import com.lisdev.account.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toCreateAccount(CreateAccountCommand body, String createdBy) {
        return Account.createNew(
                body.getCustomerId(),
                body.getAccountTypeId(),
                body.getBalance(),
                createdBy);
    }
}
