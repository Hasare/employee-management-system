# Employee Management System (Mini ERP)

A web-based Employee Management System built with Spring Boot, Thymeleaf and PostgreSQL.

---

## Current Status

- Employee CRUD functionality is fully implemented
- Focus on clean structure and maintainability
- UI is intentionally simple (no JavaScript)

---

## Features

- Create new employees
- View a list of employees
- Update existing employee data
- Delete employees with confirmation
- Server-side validation
- Clean separation using DTOs

---

## Future Improvements

- Department management
- Role management

---

## Tech Stack

- Java 21 
- Spring Boot
- Spring MVC
- Spring Data JPA (Hibernate)
- PostgreSQL
- Thymeleaf
- Maven
- Lombok

---

## Architecture

The project follows a layered architecture:

- Controller layer – handles HTTP requests and form binding
- Service layer – contains business logic
- Repository layer – handles database operations
- DTO layer – separates web input from persistence models

Flow:

View (Thymeleaf)
→ Controller
→ DTO
→ Service
→ Entity
→ Repository
→ Database

---

## Setup

1. Create PostgreSQL database (`ems_dev`)
2. Create user (`ems_user`)
3. Set environment variables:
    - DATASOURCE_URL
    - DATASOURCE_USERNAME
    - DATASOURCE_PASSWORD
4. Run the application


