package com.hasare.employeemanagement.service.exception;

public class DuplicateEmployeeEmailException extends BusinessException  {

    public DuplicateEmployeeEmailException(String email) {
        super("Employee with email already exists: " + email);
    }
}