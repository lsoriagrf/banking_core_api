package com.lisdev.account.domain.exception;

import com.lisdev.account.domain.Messages;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super(Messages.ACCOUNT_NOT_FOUND);
    }
}
