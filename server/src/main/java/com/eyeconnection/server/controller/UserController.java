package com.eyeconnection.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eyeconnection.server.dao.UserRepository;
import com.eyeconnection.server.entity.User;

@Controller
public class UserController {
    private UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/sign_up")
    public @ResponseBody User signUp(@RequestBody User user) {
        return userRepo.save(user);
    }
}
