package com.hasare.employeemanagement.domain;

import com.hasare.employeemanagement.domain.exception.InvalidDepartmentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Getter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String departmentName;

    private Department(String name) {
        String normalizedDepartmentName = normalizeRequiredDepartmentText(name, "Department name");
        this.departmentName = normalizedDepartmentName;
    }
     public static Department create(String departmentName){

        return new Department(departmentName);
     }

     public void updateDepartmentName (String departmentName){

       this.departmentName = normalizeRequiredDepartmentText(departmentName, "Department name");
     }

    private static String normalizeRequiredDepartmentText( String value, String fieldName ){
        if(value == null || value.trim().isEmpty()){
            throw new InvalidDepartmentException(fieldName + " must not be null or empty");
        }
        return value.trim();
    }


}