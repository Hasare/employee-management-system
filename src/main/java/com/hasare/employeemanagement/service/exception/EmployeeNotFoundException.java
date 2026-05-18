package com.hasare.employeemanagement.service.exception;

public class EmployeeNotFoundException extends ResourceNotFoundException  {

    public EmployeeNotFoundException(Long id) {
        super("Employee not found with id: " + id);
    }
}