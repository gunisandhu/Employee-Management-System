package com.awesome.employeeManagement.service;

import com.awesome.employeeManagement.model.Users;
import com.awesome.employeeManagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public boolean checkUsernameDoesNotExists(String username){
        return (userRepo.findByUsername(username)==null);
    }

    public void createUser(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<Users> getUsers(){
        return userRepo.findAll();
    }

    public boolean deleteUser(int id){
        var userOptional = userRepo.findById(id);
        userOptional.ifPresentOrElse(user->userRepo.delete(user),()->{throw new RuntimeException("User not found");});
        return userOptional.isPresent();
    }

    public Optional<Users> getUserById(int id){
        return userRepo.findById(id);
    }

    public boolean deleteUserByName(String username){
        var user = userRepo.findByUsername(username);
        if(user!=null){
            userRepo.deleteByUsername(username);
            return true;
        }
        return false;
    }

    public boolean verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        return authentication.isAuthenticated();
    }
}
