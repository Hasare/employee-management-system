package com.hasare.employeemanagement.web;


import com.hasare.employeemanagement.service.DepartmentService;
import com.hasare.employeemanagement.service.EmployeeService;
import com.hasare.employeemanagement.service.exception.DepartmentNotFoundException;
import com.hasare.employeemanagement.service.exception.DuplicateEmployeeEmailException;
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
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // =========================
    // LIST
    // =========================
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    // =========================
    // CREATE
    // =========================
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeCreateDto());
        addDepartmentOptions(model);
        return "employees/create";
    }

    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") EmployeeCreateDto employee,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            addDepartmentOptions(model);
            return "employees/create";
        }

        try {
            employeeService.create(employee);
        } catch (DuplicateEmployeeEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            addDepartmentOptions(model);
            return "employees/create";
        } catch (DepartmentNotFoundException ex) {
            bindingResult.rejectValue("departmentId", "invalid", ex.getMessage());
            addDepartmentOptions(model);
            return "employees/create";
        }


        return "redirect:/employees";
    }

    // =========================
    // EDIT
    // =========================
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEditDto(id));
        model.addAttribute("employeeId", id);
        addDepartmentOptions(model);
        return "employees/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") EmployeeUpdateDto employeeUpdateDto,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeId", id);
            addDepartmentOptions(model);
            return "employees/edit";
        }

        try {
            employeeService.update(id, employeeUpdateDto);
        } catch (DuplicateEmployeeEmailException ex) {
            bindingResult.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("employeeId", id);
            addDepartmentOptions(model);
            return "employees/edit";
        } catch (DepartmentNotFoundException ex) {
            bindingResult.rejectValue("departmentId", "invalid", ex.getMessage());
            model.addAttribute("employeeId", id);
            addDepartmentOptions(model);
            return "employees/edit";
        }

        return "redirect:/employees";
    }

    // =========================
    // DELETE
    // =========================
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    // =========================
    // HELPER
    // =========================
    private void addDepartmentOptions(Model model) {
        model.addAttribute("departments", departmentService.findAll());
    }
}