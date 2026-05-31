package com.hasare.employeemanagement.domain;

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
    void create_ShouldThrowException_WhenFirstNameIsBlank(){
        assertThatThrownBy(()-> Employee.create(
                " ",
                "Wick",
                "johnwick@gmail.com",
                LocalDate.of(2020,01,01),
                null
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("First name must not be null or empty");
    }



}
