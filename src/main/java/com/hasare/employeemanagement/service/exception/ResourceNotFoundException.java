package com.hasare.employeemanagement.service.exception;

public abstract class ResourceNotFoundException extends BusinessException {

    protected ResourceNotFoundException(String message) {
        super(message);
    }
}