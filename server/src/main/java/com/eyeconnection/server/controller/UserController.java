package com.eyeconnection.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.dao.UserRepository;
import com.eyeconnection.server.entity.User;

@Controller
public class UserController {
    private UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> signUp(@RequestBody User newUser) {
        String email = newUser.getEmail();
        User findResult = userRepo.findByEmail(email);
        
        //check if user is already exists
        if(findResult != null) {
            return ResponseEntity.status(202).body("User already exists with this email: " + email);
        }

        User savedUser = userRepo.save(newUser);
        return ResponseEntity.status(201).body("User created successfully " + savedUser.getEmail());
    }
}
