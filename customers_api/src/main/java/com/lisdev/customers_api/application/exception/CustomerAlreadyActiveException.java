package com.lisdev.customers_api.application.exception;

import com.lisdev.customers_api.application.Messages;

public class CustomerAlreadyActiveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerAlreadyActiveException() {
        super(Messages.CUSTOMER_ALREADY_ACTIVE);
    }

}
