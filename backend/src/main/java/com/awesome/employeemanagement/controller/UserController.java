package com.awesome.employeemanagement.controller;

import com.awesome.employeemanagement.model.Users;
import com.awesome.employeemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user, BindingResult exception) {
        System.out.println("Received user: " + user);
        if (exception.hasErrors()) {
            // Previously, we only returned a list of error messages without specifying the field names.
// Keeping this here for reference, but we now use a map to associate each field with its corresponding error.
//
// var errors = exception.getAllErrors().stream()
//         .map(DefaultMessageSourceResolvable::getDefaultMessage)
//         .collect(Collectors.toList());
//
// Now, we return a map with field names and their respective errors.

            Map<String, Object> errorResponse = new HashMap<>();
            Map<String, List<String>> fieldErrors = new HashMap<>();
            exception.getFieldErrors().forEach(error ->
                    fieldErrors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage())

            );
            errorResponse.put("status", "error"); // Indicate an error occurred
            errorResponse.put("errors", fieldErrors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (userService.checkUsernameDoesNotExists(user.getUsername())) {
            userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            return ResponseEntity.ok().body(response);
        }
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("username", "Username already exists");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        var token = userService.verify(user);
        //Not having this as Spring takes care in case of invalid request
//        if(token==null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers() {
        var users = userService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok("User deleted Successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        var user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/users/byName/{name}")
    public ResponseEntity<String> deleteUserByName(@PathVariable String name) {
        if (userService.deleteUserByName(name)) {
            return ResponseEntity.ok("User deleted Successfully");
        }
        return ResponseEntity.notFound().build();
    }


}
