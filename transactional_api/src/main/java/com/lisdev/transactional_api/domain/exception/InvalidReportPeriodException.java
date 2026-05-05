package com.lisdev.transactional_api.domain.exception;

public class InvalidReportPeriodException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidReportPeriodException(String message) {
        super(message);
    }
}
