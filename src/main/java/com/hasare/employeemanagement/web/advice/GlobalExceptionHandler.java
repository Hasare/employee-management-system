package com.hasare.employeemanagement.web.advice;

import com.hasare.employeemanagement.service.exception.ResourceNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(
            ResourceNotFoundException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/not-found";
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneralException(
            Exception ex,
            Model model
    ) {
        model.addAttribute(
                "errorMessage",
                "An unexpected error occurred."
        );

        return "error/general-error";
    }
}