package com.mybank.exceptions;

public class UnexistingCustomerException extends RuntimeException {
    @Override
    public String getMessage() {
        return "(CC) (CustomerService-UnexistingCustomerException) Customer not found!";
    }
}
