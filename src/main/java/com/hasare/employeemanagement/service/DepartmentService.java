package com.hasare.employeemanagement.service;

import com.hasare.employeemanagement.domain.Department;
import com.hasare.employeemanagement.repository.DepartmentRepository;
import com.hasare.employeemanagement.web.dto.DepartmentCreateDto;
import com.hasare.employeemanagement.web.dto.DepartmentUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    public Department create(DepartmentCreateDto departmentCreateDto) {
        String name = departmentCreateDto.getName().trim();

        if (departmentRepository.existsByName(name)) {
            throw new IllegalArgumentException("Department name already exists");
        }

        Department department = new Department(name);

        return departmentRepository.save(department);
    }

    public DepartmentUpdateDto getEditDto(Long id) {
        Department department = findById(id);

        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto();
        departmentUpdateDto.setName(department.getName());

        return departmentUpdateDto;
    }

    public Department update(Long id, DepartmentUpdateDto departmentUpdateDto) {
        Department department = findById(id);

        String name = departmentUpdateDto.getName().trim();

        boolean nameChanged = !Objects.equals(department.getName(), name);

        if (nameChanged && departmentRepository.existsByName(name)) {
            throw new IllegalArgumentException("Department name already exists");
        }

        department.setName(name);

        return departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department not found");
        }

        departmentRepository.deleteById(id);
    }
}