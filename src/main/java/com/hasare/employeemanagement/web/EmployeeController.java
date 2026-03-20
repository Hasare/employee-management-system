package com.hasare.employeemanagement.web;


import com.hasare.employeemanagement.service.EmployeeService;
import com.hasare.employeemanagement.web.dto.EmployeeCreateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new EmployeeCreateDto());
        return "employees/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("employee") EmployeeCreateDto employee, BindingResult bindingResult) {
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
}
