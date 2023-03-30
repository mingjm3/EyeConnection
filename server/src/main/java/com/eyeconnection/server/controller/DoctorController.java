package com.eyeconnection.server.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.dao.DoctorRepository;
import com.eyeconnection.server.entity.Doctor;
import com.eyeconnection.server.service.DoctorService;

@Controller 
class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private DoctorRepository doctorRepo;
    private DoctorService doctorService;
    
    public DoctorController(DoctorRepository doctorRepo, DoctorService doctorService) {
        this.doctorRepo = doctorRepo;
        this.doctorService = doctorService;
    }

    @PostMapping("/doctor_log_in")
    public ResponseEntity<String> userLogIn(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        Doctor findResult = doctorRepo.findByEmail(email);
        logger.info(String.format("Request to /doctor_log_in %s", requestBody.toString()));

        if(findResult == null || !findResult.getPassword().equals(password)) {
            logger.warn(String.format("Doctor log in failed: %s", requestBody.toString()));
            return ResponseEntity.status(401).body("Email or password incorrect");
        }

        logger.info(String.format("Doctor log in successfully: %s", findResult.toString()));
        return ResponseEntity.status(200).body("User logged in successfully");
    }
}