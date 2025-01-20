package com.yome.johnbosco_management.exceptions;

public class UnpaidLoanException extends RuntimeException {
    public UnpaidLoanException(String message) {
        super(message);
    }
}
