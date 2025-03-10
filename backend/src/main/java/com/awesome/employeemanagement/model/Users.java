package com.awesome.employeemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
public class Users {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must contain only letters, numbers, and underscores")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long ")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[!@#\\$%\\^&\\*].*", message = "Password must contain at least one special character (e.g., !, @, #, $, etc.)")
    @Pattern(regexp = "^(?!.*\\s).*$", message = "Password cannot contain spaces")
    @Column(nullable = false)
    private String password;

//    @Column(name="password")
//    private String hashedPassword;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "First name cannot be empty")
    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabetic characters")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only alphabets")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
