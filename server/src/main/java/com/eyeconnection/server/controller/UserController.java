package com.eyeconnection.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.dao.UserRepository;
import com.eyeconnection.server.entity.User;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> signUp(@RequestBody User newUser) {
        logger.info(String.format("Request to /sign_up: %s", newUser.toString()));
        String email = newUser.getEmail();
        User findResult = userRepo.findByEmail(email);
        
        //check if user is already exists
        if(findResult != null) {
            logger.info(String.format("User saved rejected [user already exists]: %s", newUser.toString()));
            return ResponseEntity.status(202).body("User already exists with this email: " + email);
        }

        User savedUser = userRepo.save(newUser);
        logger.info(String.format("User saved: %s", savedUser.toString()));
        return ResponseEntity.status(201).body("User created successfully " + savedUser.getEmail());
    }
}
