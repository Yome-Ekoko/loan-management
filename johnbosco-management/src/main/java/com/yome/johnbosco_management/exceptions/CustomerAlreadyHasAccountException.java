package com.yome.johnbosco_management.exceptions;

public class CustomerAlreadyHasAccountException extends RuntimeException {
    public CustomerAlreadyHasAccountException(String message) {
        super(message);
    }
}
