package com.hasare.employeemanagement.service;

import com.hasare.employeemanagement.domain.Department;
import com.hasare.employeemanagement.repository.DepartmentRepository;
import com.hasare.employeemanagement.service.exception.DepartmentNotFoundException;
import com.hasare.employeemanagement.service.exception.DuplicateDepartmentNameException;
import com.hasare.employeemanagement.web.dto.DepartmentCreateDto;
import com.hasare.employeemanagement.web.dto.DepartmentUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    public Department create(DepartmentCreateDto departmentCreateDto) {
        String name = departmentCreateDto.getName().trim();

        if (departmentRepository.existsByNameIgnoreCase(name)) {
            throw  new DuplicateDepartmentNameException(name);
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


        if (departmentRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateDepartmentNameException(name);
        }

        department.setName(name);

        return departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }

        departmentRepository.deleteById(id);
    }
}