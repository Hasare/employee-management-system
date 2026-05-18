package com.hasare.employeemanagement.service.exception;

public class DepartmentNotFoundException extends ResourceNotFoundException  {

    public DepartmentNotFoundException(Long id) {
        super("Department not found with id: " + id);
    }
}