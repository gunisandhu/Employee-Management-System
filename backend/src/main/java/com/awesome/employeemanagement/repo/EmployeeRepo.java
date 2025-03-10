package com.awesome.employeemanagement.repo;

import com.awesome.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    boolean existsEmployeeByEmail(String email);
}
