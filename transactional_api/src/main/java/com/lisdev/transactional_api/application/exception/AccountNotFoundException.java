package com.lisdev.transactional_api.application.exception;

import com.lisdev.transactional_api.application.Messages;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super(Messages.ACCOUNT_NOT_FOUND);
    }
}
