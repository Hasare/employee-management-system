package com.hasare.employeemanagement.domain.exception;

public class InvalidEmployeeException extends RuntimeException{

    public InvalidEmployeeException(String message){

        super(message);
    }
}
