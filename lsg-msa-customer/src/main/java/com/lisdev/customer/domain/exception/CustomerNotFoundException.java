package com.lisdev.customer.domain.exception;

import com.lisdev.customer.domain.Messages;

public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(String identification) {
        super(Messages.CUSTOMER_NOT_FOUND + identification);
    }

    public CustomerNotFoundException(Integer customerId) {
        super(Messages.CUSTOMER_ID_NOT_EXIST + customerId);
    }

}
