package com.hasare.employeemanagement.domain;

import com.hasare.employeemanagement.domain.exception.InvalidDepartmentException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class DepartmentTest {

    @Test
    void create_shouldNormalizeDepartmentName() {
        Department department = Department.create("  Human Resources  ");

        assertThat(department.getDepartmentName()).isEqualTo("Human Resources");
    }

    @Test
    void create_shouldThrowException_whenNameIsBlank() {
        assertThatThrownBy(() -> Department.create("   "))
                .isInstanceOf(InvalidDepartmentException.class)
                .hasMessage("Department name must not be null or empty");
    }

    @Test
    void create_shouldThrowException_whenNameIsNull() {
        assertThatThrownBy(() -> Department.create(null))
                .isInstanceOf(InvalidDepartmentException.class)
                .hasMessage("Department name must not be null or empty");
    }

    @Test
    void updateName_shouldNormalizeDepartmentName() {
        Department department = Department.create("HR");

        department.updateDepartmentName("  Finance  ");

        assertThat(department.getDepartmentName()).isEqualTo("Finance");
    }

    @Test
    void updateName_shouldThrowException_whenNameIsBlank() {
        Department department = Department.create("HR");

        assertThatThrownBy(() -> department.updateDepartmentName("   "))
                .isInstanceOf(InvalidDepartmentException.class)
                .hasMessage("Department name must not be null or empty");
    }

    @Test
    void updateName_shouldThrowException_whenNameIsNull() {
        Department department = Department.create("HR");

        assertThatThrownBy(() -> department.updateDepartmentName(null))
                .isInstanceOf(InvalidDepartmentException.class)
                .hasMessage("Department name must not be null or empty");
    }
}