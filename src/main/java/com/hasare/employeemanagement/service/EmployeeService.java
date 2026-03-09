package com.hasare.employeemanagement.service;

import com.hasare.employeemanagement.domain.Employee;
import com.hasare.employeemanagement.repository.EmployeeRepository;
import com.hasare.employeemanagement.web.dto.EmployeeCreateDto;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll (){
        return employeeRepository.findAll();
    }

    public Employee create (EmployeeCreateDto dto){
        String email = dto.getEmail();

        if (email != null && !email.isBlank() && employeeRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail().trim());

        if( dto.getHiredAt() == null) {
            employee.setHiredAt(LocalDate.now());
        } else {
            employee.setHiredAt(dto.getHiredAt());
        }
        return employeeRepository.save(employee);

    }
}
