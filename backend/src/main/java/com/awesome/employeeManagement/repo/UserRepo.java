package com.awesome.employeeManagement.repo;

import com.awesome.employeeManagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    Users deleteByUsername(String username);
}
