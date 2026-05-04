package com.hasare.employeemanagement.web.mapper;

import com.hasare.employeemanagement.domain.Employee;
import com.hasare.employeemanagement.web.dto.EmployeeUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeUpdateDto toUpdateDto (Employee employee){

        EmployeeUpdateDto dto = new EmployeeUpdateDto();

        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setHiredAt(employee.getHiredAt());
        dto.setDepartmentId(
                employee.getDepartment() != null
                        ? employee.getDepartment().getId()
                        : null
        );

        return dto;
    }
}
