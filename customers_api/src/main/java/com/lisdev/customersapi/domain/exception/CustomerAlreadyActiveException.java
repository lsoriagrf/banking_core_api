package com.lisdev.customersapi.domain.exception;

import com.lisdev.customersapi.domain.Messages;

public class CustomerAlreadyActiveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerAlreadyActiveException() {
        super(Messages.CUSTOMER_ALREADY_ACTIVE);
    }

}
