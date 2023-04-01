package com.eyeconnection.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AppointmentRepo;
import com.eyeconnection.server.dao.AvailableDatesRepo;
import com.eyeconnection.server.dao.DoctorRepo;
import com.eyeconnection.server.entity.Appointment;
import com.eyeconnection.server.entity.AvailableDates;
import com.eyeconnection.server.entity.Doctor;
import com.eyeconnection.server.enums.AppointmentStatus;
import com.eyeconnection.server.exceptions.AppointmentNotFoundException;

@Service
public class DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final AvailableDatesRepo availableDatesRepo;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    
    public DoctorService(AvailableDatesRepo availabledDatesRepo, AppointmentRepo appointmentRepo, DoctorRepo doctorRepo) {
        this.availableDatesRepo = availabledDatesRepo;
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
    }

    public Boolean doctorLogin(String email, String password) {
        Doctor findResult = doctorRepo.findByEmail(email);
        return findResult != null && findResult.getPassword().equals(password);
    }

    public ResponseEntity<String> updateAvailableDates(Long doctorSysId, LocalDateTime[] newAvailabeDates) {
        ArrayList<AvailableDates> newAvailableDatesRecords = new ArrayList<>();
        for(LocalDateTime date : newAvailabeDates) {
            newAvailableDatesRecords.add(new AvailableDates(doctorSysId, date));
        }
        
        try {
            availableDatesRepo.deleteByDoctorSysId(doctorSysId);
            availableDatesRepo.saveAllAndFlush(newAvailableDatesRecords);
        } catch (Exception e) {
            logger.error(String.format("Updated available dates failed: %s", e.getMessage()));
            return ResponseEntity.status(500).body("Updated available dates failed " + e.getMessage());
        }

        logger.info(String.format("Updated available dates successfully: [%s]", doctorSysId));
        return ResponseEntity.status(200).body("Updated available dates successfully");
    }

    public ResponseEntity<String> confirmAppointment(Long appointmentId, String onlineMeeting, String notes) {
        try {
            Appointment ret = appointmentRepo.findById(appointmentId).map(
                appointment -> {
                    appointment.setAppointmentStatus(AppointmentStatus.APPROVED.toString());
                    appointment.setOnlineMeeting(onlineMeeting);
                    appointment.setNotes(notes);
                    return appointmentRepo.save(appointment);
                }
            ).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

            if(ret == null) {
                return ResponseEntity.status(500).body("Confirmation failed");
            }
            return ResponseEntity.status(200).body("Confirmation successful");
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(404).body("Appointment not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Confirmation failed: " + e.getMessage());
        }
    }
}
