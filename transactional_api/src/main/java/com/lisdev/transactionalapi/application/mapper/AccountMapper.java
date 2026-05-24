package com.lisdev.transactionalapi.application.mapper;

import com.lisdev.transactionalapi.application.port.in.command.CreateAccountCommand;
import com.lisdev.transactionalapi.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toCreateAccount(CreateAccountCommand body, Integer customerId) {
        return Account.createNew(
                customerId,
                body.getAccountTypeId(),
                body.getBalance(),
                body.getIdentification());
    }
}
