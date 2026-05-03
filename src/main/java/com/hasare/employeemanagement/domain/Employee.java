package com.hasare.employeemanagement.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate hiredAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "department_id")
    private Department department;

    private Employee(String firstName,
                     String lastName,
                     String email,
                     LocalDate hiredAt,
                     Department department) {
        String normalizedFirstName = normalizeRequiredText(firstName, "First name");
        String normalizedLastName = normalizeRequiredText(lastName, "Last name");
        String normalizedEmail = normalizeEmail(email);
        LocalDate validHiredAt = requireHiredAt(hiredAt);

        this.firstName = normalizedFirstName;
        this.lastName = normalizedLastName;
        this.email = normalizedEmail;
        this.hiredAt = validHiredAt;
        this.department = department;
    }

    public static Employee create(String firstName,
                                  String lastName,
                                  String email,
                                  LocalDate hiredAt,
                                  Department department) {
        return new Employee(firstName, lastName, email, hiredAt, department);
    }

    public void updatePersonalData(String firstName, String lastName, String email) {
        String normalizedFirstName = normalizeRequiredText(firstName, "First name");
        String normalizedLastName = normalizeRequiredText(lastName, "Last name");
        String normalizedEmail = normalizeEmail(email);

        this.firstName = normalizedFirstName;
        this.lastName = normalizedLastName;
        this.email = normalizedEmail;
    }

    public void updateHiredAt(LocalDate hiredAt) {
        this.hiredAt = requireHiredAt(hiredAt);
    }

    public void updateDepartment(Department department) {
        this.department = department;
    }

    // Domain-Normalisierung + Validation

    private static String normalizeRequiredText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty");
        }

        return value.trim();
    }
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    private static String normalizeEmail(String value) {
        String normalized = normalizeRequiredText(value, "Email").toLowerCase();

        if (!normalized.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return normalized;
    }
    private static LocalDate requireHiredAt(LocalDate hiredAt) {
        if (hiredAt == null) {
            throw new IllegalArgumentException("Hired date must not be null");
        }

        return hiredAt;
    }

}
