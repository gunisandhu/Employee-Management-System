package com.awesome.employeemanagement.controller;

import com.awesome.employeemanagement.model.Users;
import com.awesome.employeemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        if (userService.checkUsernameDoesNotExists(user.getUsername())) {
            userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.badRequest().body("Username already exist");
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
