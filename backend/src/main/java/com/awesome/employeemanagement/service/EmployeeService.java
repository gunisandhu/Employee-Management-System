package com.awesome.employeemanagement.service;

import com.awesome.employeemanagement.model.Employee;
import com.awesome.employeemanagement.model.Users;
import com.awesome.employeemanagement.repo.EmployeeRepo;
import com.awesome.employeemanagement.repo.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public EmployeeService(EmployeeRepo employeeRepo, UserRepo userRepo) {
        this.employeeRepo = employeeRepo;
        this.userRepo = userRepo;
    }

    public EmployeeRepo getEmployeeRepo() {
        return employeeRepo;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    public String createEmployee(Employee employee) {
        var tempPassword = generateRandomPassword();
        Users user = createUser(employee, tempPassword);
        employee.setUser(user);
        employeeRepo.save(employee);
        return tempPassword;
    }

    private Users createUser(Employee employee, String tempPassword) {
        Users user = new Users();
        user.setFirstName(employee.getFirstName());
        user.setLastName(employee.getLastName());
        user.setEmail(employee.getEmail());
        user.setUsername(employee.getUsername());
        user.setPassword(tempPassword);
        userRepo.save(user);
        return user;
    }

    private String generateRandomPassword() {
        return encoder.encode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12));
    }

    public boolean checkEmployeeExistWithEmail(String email) {
        return employeeRepo.existsEmployeeByEmail(email);
    }
}
