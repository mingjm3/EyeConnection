package com.eyeconnection.server.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyeconnection.server.dao.AppointmentRepository;
import com.eyeconnection.server.dao.AvailableDatesRepository;
import com.eyeconnection.server.dao.DoctorRepository;
import com.eyeconnection.server.entity.Doctor;
import com.eyeconnection.server.service.DoctorService;
import com.eyeconnection.server.utils.DateUtils;

@Controller 
class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private DoctorRepository doctorRepo;
    private DoctorService doctorService;
    private AvailableDatesRepository availableDatesRepository;
    private AppointmentRepository appointmentRepository;
    
    public DoctorController(DoctorRepository doctorRepo, DoctorService doctorService, AvailableDatesRepository availableDatesRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepo = doctorRepo;
        this.doctorService = doctorService;
        this.availableDatesRepository = availableDatesRepository;
        this.appointmentRepository = appointmentRepository;
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

    @PutMapping("/update_available_dates")
    public ResponseEntity<String> updateAvailableDates(@RequestBody UpdateDoctorAvailabilityRequest requestBody) {
        Long doctorSysId = requestBody.getDoctorSysId();
        LocalDateTime[] newAvailabeDates = requestBody.getNewAvailableDates();
        logger.info(String.format("Request to /update_available_dates %s", requestBody.toString()));

        doctorService = new DoctorService(availableDatesRepository, appointmentRepository);
        
        try {
            doctorService.updateAvailableDates(doctorSysId, newAvailabeDates);
        } catch (Exception e) {
            logger.error(String.format("Updated available dates failed: %s %s ", requestBody.toString(), e.getMessage()));
            return ResponseEntity.status(500).body("Updated available dates failed " + e.getMessage());
        }
        logger.error(String.format("Updated available dates successfully: %s ", requestBody.toString()));
        return ResponseEntity.status(200).body("Updated available dates successfully");
    }

    @PutMapping("/confirm_appointment")
    public ResponseEntity<String> confirmAppointment(@RequestBody Map<String, String> requestBody) {
        Long appointmentId = Long.valueOf(requestBody.get("appointment_id"));
        String onlineMeeting = requestBody.get("online_meeting");
        String notes = requestBody.get("notes");
        logger.info(String.format("Request to /confirm_appointment %s", requestBody.toString()));
        return doctorService.confirmAppointment(appointmentId, onlineMeeting, notes);
    }

    public static class UpdateDoctorAvailabilityRequest {
        private Long doctorSysId;
        private LocalDateTime[] newAvailableDates;
        
        public UpdateDoctorAvailabilityRequest(Long doctor_sys_id, String[] dates) {
            this.doctorSysId = doctor_sys_id;
            LocalDateTime[] availabeDates = new LocalDateTime[dates.length];
            for(int i = 0; i < dates.length; i++) {
                availabeDates[i] = DateUtils.getDate(dates[i]);
            }
            this.newAvailableDates = availabeDates;
        }

        public Long getDoctorSysId() {
            return doctorSysId;
        }
    
        public void setDoctorSysId(Long doctorSysId) {
            this.doctorSysId = doctorSysId;
        }

        public LocalDateTime[] getNewAvailableDates() {
            return newAvailableDates;
        }

        public void setNewAvailableDates(LocalDateTime[]newAvailableDates) {
            this.newAvailableDates = newAvailableDates;
        }

        @Override
        public String toString() {
            return "UpdateDoctorAvailabilityRequest [doctorSysId=" + doctorSysId + ", newAvailableDates="
                    + Arrays.toString(newAvailableDates) + "]";
        }
    }    
}