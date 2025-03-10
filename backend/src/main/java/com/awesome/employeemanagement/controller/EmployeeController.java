package com.awesome.employeemanagement.controller;

import com.awesome.employeemanagement.model.Employee;
import com.awesome.employeemanagement.service.EmployeeService;
import com.awesome.employeemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        var employeeById = employeeService.getEmployeeById(id);
        return employeeById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/employee/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult exception) {
        if (exception.hasErrors()) {
            Map<String, Object> errorResponse = new HashMap<>();
            Map<String, List<String>> fieldErrors = new HashMap<>();
            exception.getFieldErrors().forEach(error ->
                    fieldErrors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage())

            );
            errorResponse.put("status", "error"); // Indicate an error occurred
            errorResponse.put("errors", fieldErrors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (employeeService.checkEmployeeExistWithEmail(employee.getEmail())) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("error", "email already exist");
            return ResponseEntity.badRequest().body(error);
        }
        if (!userService.checkUsernameDoesNotExists(employee.getUsername())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("username", "Username already exists");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        var tempPassword = employeeService.createEmployee(employee);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("id", employee.getId());
        response.put("username", employee.getUsername());
        response.put("password", tempPassword);
        return ResponseEntity.ok(response);

    }
}
