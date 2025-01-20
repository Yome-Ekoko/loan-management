package com.yome.johnbosco_management.exceptions;

public class CustomerNotFoundException  extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
