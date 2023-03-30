package com.eyeconnection.server.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.entity.User;
import com.eyeconnection.server.enums.AppointmentStatus;
import com.eyeconnection.server.service.UserService;
import com.eyeconnection.server.utils.DateUtils;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<String> signUp(@RequestBody User newUser) {
        logger.info(String.format("Request to /sign_up: %s", newUser.toString()));
        User result = userService.signUp(newUser);
        
        if(result != null) {
            return ResponseEntity.status(201).body("User sign up successfully " + result.toString());
        }
        return ResponseEntity.status(202).body("User sign up failed" + newUser.toString());
    }

    @PostMapping("/user_log_in")
    public ResponseEntity<String> userLogIn(@RequestBody Map<String, String> requestBody) {
        logger.info(String.format("Request to /user_log_in: %s", requestBody.toString()));
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        if(userService.userLogIn(email, password)) {
            return ResponseEntity.status(200).body("User log in successfully");
        }
        return ResponseEntity.status(401).body("Email or password incorrect");
    }

    @PostMapping("/make_appointment")
    public ResponseEntity<String> makeAppointment(@RequestBody Map<String, String> requestBody) {
        logger.info(String.format("Request to /make_appointment: %s", requestBody.toString()));
        Long patientSysId = Long.valueOf(requestBody.get("patient_sys_id"));
        Long doctorSysId = Long.valueOf(requestBody.get("doctor_sys_id"));
        LocalDateTime appointmentDate = DateUtils.getDate(requestBody.get("appointment_date"));
        Boolean online = Boolean.valueOf(requestBody.get("online"));

        AppointmentStatus appointmentStatus = userService.makeAppointment(patientSysId, doctorSysId, appointmentDate, online);
        
        if(appointmentStatus.equals(AppointmentStatus.PENDING)) {
            logger.info(String.format("Create an appointment successfully: %s", requestBody.toString()));
            return ResponseEntity.status(201).body("Make an appointment successfully");
        }
        logger.warn(String.format("Create an appointment failed: %s", requestBody.toString()));
        return ResponseEntity.status(202).body("Make an appointment failed");
    }
}
