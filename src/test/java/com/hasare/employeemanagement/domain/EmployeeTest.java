package com.hasare.employeemanagement.domain;

import com.hasare.employeemanagement.domain.exception.InvalidEmployeeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {

    @Test
    void create_shouldNormalizeEmployeeDate(){
        Employee employee = Employee.create(
                "   John  ",
                "  Wick  ",
                "   JOHNWICK@Gmail.COM  ",
                LocalDate.of(2020,01,01),
                null
        );

        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Wick");
        assertThat(employee.getEmail()).isEqualTo("johnwick@gmail.com");
        assertThat(employee.getHiredAt()).isEqualTo(LocalDate.of(2020,01,01));
        assertThat(employee.getDepartment()).isNull();
    }

    @Test
    void create_shouldThrowException_whenFirstNameIsBlank(){
        assertThatThrownBy(()-> Employee.create(
                " ",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020,01,01),
                null
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("First name must not be null or empty");
    }

    @Test
    void create_shouldThrowException_whenLastNameIsBlank(){
        assertThatThrownBy(()-> Employee.create(
                "John",
                " ",
                "johnwick@gmail.com",
                LocalDate.of(2020,01,01),
                null
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Last name must not be null or empty");
    }

    @Test
    void create_shouldThrowException_whenEmailIsBlank() {
        assertThatThrownBy(() -> Employee.create(
                "John",
                "Wick",
                " ",
                LocalDate.of(2020, 1, 1),
                null
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Email must not be null or empty");
    }

    @Test
    void create_shouldThrowException_whenEmailFormatIsInvalid() {
        assertThatThrownBy(() -> Employee.create(
                "John",
                "Wick",
                "johnwick-gmail.com",
                LocalDate.of(2020, 1, 1),
                null
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Invalid email format");
    }

    @Test
    void create_shouldThrowException_whenHiredAtIsNull() {
        assertThatThrownBy(() -> Employee.create(
                "John",
                "Wick",
                "johnwick@gmail.com",
                null,
                null
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Hired date must not be null");
    }

    @Test
    void updatePersonalData_shouldNormalizeEmployeeData() {
        Employee employee = Employee.create(
                "John",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020, 1, 1),
                null
        );

        employee.updatePersonalData(
                "   Jonathan  ",
                "  Doe  ",
                "   JONATHAN@GMAIL.COM  "
        );

        assertThat(employee.getFirstName()).isEqualTo("Jonathan");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getEmail()).isEqualTo("jonathan@gmail.com");
    }

    @Test
    void updatePersonalData_shouldThrowException_whenEmailFormatIsInvalid() {
        Employee employee = Employee.create(
                "John",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020, 1, 1),
                null
        );

        assertThatThrownBy(() -> employee.updatePersonalData(
                "John",
                "Wick",
                "john-gmail.com"
        ))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Invalid email format");
    }

    @Test
    void updateHiredAt_shouldUpdateHiredAt() {
        Employee employee = Employee.create(
                "John",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020, 1, 1),
                null
        );

        employee.updateHiredAt(LocalDate.of(2021, 5, 10));

        assertThat(employee.getHiredAt()).isEqualTo(LocalDate.of(2021, 5, 10));
    }

    @Test
    void updateHiredAt_shouldThrowException_whenHiredAtIsNull() {
        Employee employee = Employee.create(
                "John",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020, 1, 1),
                null
        );

        assertThatThrownBy(() -> employee.updateHiredAt(null))
                .isInstanceOf(InvalidEmployeeException.class)
                .hasMessage("Hired date must not be null");
    }
}
