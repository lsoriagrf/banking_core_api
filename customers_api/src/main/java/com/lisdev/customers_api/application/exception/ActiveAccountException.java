package com.lisdev.customers_api.application.exception;

import com.lisdev.customers_api.application.Messages;

public class ActiveAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ActiveAccountException() {
        super(Messages.ACTIVE_ACCOUNT);
    }

}
