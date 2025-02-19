package com.awesome.employeemanagement.repo;

import com.awesome.employeemanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    Users deleteByUsername(String username);
}
