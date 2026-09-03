package com.lisdev.account.domain.exception;

public class InvalidReportPeriodException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidReportPeriodException(String message) {
        super(message);
    }
}
