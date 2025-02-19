package com.awesome.employeemanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class home {

    @GetMapping("/")
    public String appHome(HttpServletRequest request) {
        return "Welcome to Spring Boot\n"+request.getSession().getId();
    }

}
