package com.hsbc.challenge.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee id not found : " + id);
    }
}
