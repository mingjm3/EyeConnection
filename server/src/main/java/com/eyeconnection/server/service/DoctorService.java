package com.eyeconnection.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AppointmentRepository;
import com.eyeconnection.server.dao.AvailableDatesRepository;
import com.eyeconnection.server.entity.AvailableDates;
import com.eyeconnection.server.enums.AppointmentStatus;
import com.eyeconnection.server.exceptions.AppointmentNotFoundException;

@Service
public class DoctorService {
    private final AvailableDatesRepository availableDatesRepository;
    private final AppointmentRepository appointmentRepository;
    
    public DoctorService(AvailableDatesRepository availabledDatesRepository, AppointmentRepository appointmentRepository) {
        this.availableDatesRepository = availabledDatesRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void updateAvailableDates(Long doctorSysId, LocalDateTime[] newAvailabeDates) throws Exception {
        ArrayList<AvailableDates> newAvailableDatesRecords = new ArrayList<>();
        for(LocalDateTime date : newAvailabeDates) {
            newAvailableDatesRecords.add(new AvailableDates(doctorSysId, date));
        }
        availableDatesRepository.deleteByDoctorSysId(doctorSysId);
        availableDatesRepository.saveAllAndFlush(newAvailableDatesRecords);
    }

    public ResponseEntity<String> confirmAppointment(Long appointmentId, String onlineMeeting, String notes) {
        try {
            appointmentRepository.findById(appointmentId).map(
                appointment -> {
                    appointment.setAppointmentStatus(AppointmentStatus.APPROVED.toString());
                    appointment.setOnlineMeeting(onlineMeeting);
                    appointment.setNotes(notes);
                    return appointmentRepository.save(appointment);
                }
            ).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(404).body("Appointment not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Confirmation failed: " + e.getMessage());
        }
        return ResponseEntity.status(200).body("Confirmation successful");
    }
}
