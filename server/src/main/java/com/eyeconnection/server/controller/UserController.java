package com.eyeconnection.server.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.dao.UserRepository;
import com.eyeconnection.server.entity.User;
import com.eyeconnection.server.enums.AppointmentStatus;
import com.eyeconnection.server.service.UserService;
import com.eyeconnection.server.utils.DateUtils;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepo;
    private UserService userService;

    public UserController(UserRepository userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
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

    @PostMapping("/user_log_in")
    public ResponseEntity<?> userLogIn(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        logger.info(String.format("Request to /user_log_in: %s", email));
        User findResult = userRepo.findByEmail(email);
        
        //check if user is existing and password is correct
        if(findResult == null || !findResult.getPassword().equals(password)) {
            logger.error(String.format("User log in failed: %s ", email));
            return ResponseEntity.status(401).body("Email or password incorrect");
        }

        logger.info(String.format("User logged in successfully: %s", email));
        return ResponseEntity.status(200).body("User logged in successfully");
    }

    @PostMapping("/make_appointment")
    public ResponseEntity<?> makeAppointment(@RequestBody Map<String, String> requestBody) {
        Long patientSysId = Long.valueOf(requestBody.get("patient_sys_id"));
        Long doctorSysId = Long.valueOf(requestBody.get("doctor_sys_id"));
        LocalDateTime appointmentDate = DateUtils.getDate(requestBody.get("appointment_date"));
        Boolean online = Boolean.valueOf(requestBody.get("online"));

        logger.info(String.format("Request to /make_appointment: [%s]", patientSysId));

        AppointmentStatus appointmentStatus = userService.makeAppointment(patientSysId, doctorSysId, appointmentDate, online);
        
        if(appointmentStatus.equals(AppointmentStatus.PENDING)) {
            return ResponseEntity.status(201).body("Make an appointment successfully");
        }
        return ResponseEntity.status(202).body("Make an appointment failed");
    }
}
