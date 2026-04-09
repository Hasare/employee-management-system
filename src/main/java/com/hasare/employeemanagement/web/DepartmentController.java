package com.hasare.employeemanagement.web;


import com.hasare.employeemanagement.service.DepartmentService;
import com.hasare.employeemanagement.web.dto.DepartmentCreateDto;
import com.hasare.employeemanagement.web.dto.DepartmentUpdateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/list";
    }

    @GetMapping("/new")
    public String showCreateDepartmentForm(Model model) {
        model.addAttribute("department", new DepartmentCreateDto());
        return "departments/create";
    }

    @PostMapping
    public String createDepartment(
            @Valid @ModelAttribute("department") DepartmentCreateDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "departments/create";
        }

        try {
            departmentService.create(dto);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("name", "duplicate", ex.getMessage());
            return "departments/create";
        }

        return "redirect:/departments";
    }

    @GetMapping("/{id}/edit")
    public String showDepartmentEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getEditDto(id));
        model.addAttribute("departmentId", id);
        return "departments/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateDepartment(
            @PathVariable Long id,
            @Valid @ModelAttribute("department") DepartmentUpdateDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departmentId", id);
            return "departments/edit";
        }

        try {
            departmentService.update(id, dto);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("name", "duplicate", ex.getMessage());
            model.addAttribute("departmentId", id);
            return "departments/edit";
        }

        return "redirect:/departments";
    }


    @GetMapping("/{id}/delete")
    public String showDepartmentDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        return "departments/delete";
    }


    @PostMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteById(id);
        return "redirect:/departments";
    }
}