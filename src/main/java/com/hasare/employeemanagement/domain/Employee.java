package com.hasare.employeemanagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
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

    private LocalDate hiredAt;

    public Employee(String firstName, String lastName, String email, LocalDate hiredAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hiredAt = hiredAt;
    }

}
