package com.hasare.employeemanagement.service;

import com.hasare.employeemanagement.domain.Employee;
import com.hasare.employeemanagement.repository.EmployeeRepository;
import com.hasare.employeemanagement.web.dto.EmployeeCreateDto;
import com.hasare.employeemanagement.web.dto.EmployeeUpdateDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    public Employee create(EmployeeCreateDto dto) {
        String email = dto.getEmail();

        if (email != null && !email.isBlank() && employeeRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail().trim());

        if (dto.getHiredAt() == null) {
            employee.setHiredAt(LocalDate.now());
        } else {
            employee.setHiredAt(dto.getHiredAt());
        }
        return employeeRepository.save(employee);
    }
    public EmployeeUpdateDto getEditDto(Long id) {
        Employee employee = findById(id);

        EmployeeUpdateDto dto = new EmployeeUpdateDto();

        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setHiredAt(employee.getHiredAt());

        return dto;
    }
    public Employee update(Long id, EmployeeUpdateDto dto) {
        Employee employee = findById(id);

        String email = dto.getEmail().trim().toLowerCase();

        boolean emailChanged = !java.util.Objects.equals(employee.getEmail(), email);

        if (emailChanged && employeeRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        employee.setFirstName(dto.getFirstName().trim());
        employee.setLastName(dto.getLastName().trim());
        employee.setEmail(email);
        employee.setHiredAt(dto.getHiredAt());

        return employeeRepository.save(employee);
    }
}
