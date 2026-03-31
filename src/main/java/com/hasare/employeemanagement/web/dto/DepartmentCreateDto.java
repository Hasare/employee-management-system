package com.hasare.employeemanagement.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentCreateDto {

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must be at most 100 characters")
    private String name;
}