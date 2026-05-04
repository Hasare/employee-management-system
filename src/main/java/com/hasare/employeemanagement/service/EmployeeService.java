package com.hasare.employeemanagement.service;

import com.hasare.employeemanagement.domain.Department;
import com.hasare.employeemanagement.domain.Employee;
import com.hasare.employeemanagement.repository.DepartmentRepository;
import com.hasare.employeemanagement.repository.EmployeeRepository;
import com.hasare.employeemanagement.web.dto.EmployeeCreateDto;
import com.hasare.employeemanagement.web.dto.EmployeeUpdateDto;
import com.hasare.employeemanagement.web.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper= employeeMapper;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    public Employee create(EmployeeCreateDto dto) {
        String email = dto.getEmail().trim().toLowerCase();

        if ( employeeRepository.existsByEmail(email) ) {
            throw new IllegalArgumentException("Email already exists");
        }

        LocalDate hiredAt = dto.getHiredAt() != null ? dto.getHiredAt() : LocalDate.now();

        Department department = resolveDepartment(dto.getDepartmentId());

        Employee employee = Employee.create(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                hiredAt,
                department);

        return employeeRepository.save(employee);
    }

    public EmployeeUpdateDto getEditDto(Long id) {
        Employee employee = findById(id);
        return employeeMapper.toUpdateDto(employee);
    }

    public Employee update(Long id, EmployeeUpdateDto dto) {

        Employee employee = findById(id);

        String email = dto.getEmail().trim().toLowerCase();
        boolean emailChanged = !java.util.Objects.equals(employee.getEmail(), email);

        if ( emailChanged && employeeRepository.existsByEmail(email) ) {
            throw new IllegalArgumentException("Email already exists");
        }

        Department department = resolveDepartment(dto.getDepartmentId());

        employee.updatePersonalData(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );
        employee.updateHiredAt(dto.getHiredAt());
        employee.updateDepartment(department);

        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        if ( !employeeRepository.existsById(id) ) {
            throw new IllegalArgumentException("Employee not found");
        }

        employeeRepository.deleteById(id);
    }

    private Department resolveDepartment(Long departmentId) {

        if ( departmentId == null ) {
            return null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }
}
