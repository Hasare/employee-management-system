package com.hasare.employeemanagement.web;


import com.hasare.employeemanagement.service.EmployeeService;
import com.hasare.employeemanagement.web.dto.EmployeeCreateDto;
import com.hasare.employeemanagement.web.dto.EmployeeUpdateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees (Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeCreateDto());
        return "employees/create";
    }

    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") EmployeeCreateDto employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/create";
        }

        try {
            employeeService.create(employee);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            return "employees/create";
        }

        return "redirect:/employees";
    }
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        EmployeeUpdateDto employeeUpdateDto = employeeService.getEditDto(id);
        model.addAttribute("employee", employeeUpdateDto);
        model.addAttribute("employeeId", id);
        return "employees/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployee(
            @PathVariable Long id,
            @Valid @ModelAttribute("employee") EmployeeUpdateDto employeeUpdateDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeId", id);
            return "employees/edit";
        }

        try {
            employeeService.update(id, employeeUpdateDto);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("employeeId", id);
            return "employees/edit";
        }

        return "redirect:/employees";
    }
}
