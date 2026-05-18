package com.hasare.employeemanagement.service.exception;

public class DuplicateDepartmentNameException extends BusinessException {

    public DuplicateDepartmentNameException(String name) {
        super("Department name already exists: " + name);
    }
}