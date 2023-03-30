package com.eyeconnection.server.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AppointmentRepository;
import com.eyeconnection.server.dao.AvailableDatesRepository;
import com.eyeconnection.server.entity.Appointment;
import com.eyeconnection.server.entity.AvailableDates;
import com.eyeconnection.server.enums.AppointmentStatus;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private AvailableDatesRepository availableDatesRepository;
    private AppointmentRepository appointmentRepository;

    public UserService(AvailableDatesRepository availableDatesRepository, AppointmentRepository appointmentRepository) {
        this.availableDatesRepository = availableDatesRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentStatus makeAppointment(Long patientSysId, Long doctorSysId, LocalDateTime appointmentDate, Boolean online) {
        AvailableDates queryResult = availableDatesRepository.findByDoctorSysIdAndAvailableDate(doctorSysId, appointmentDate);
        if(queryResult != null) {
            Appointment newAppointment = new Appointment(doctorSysId, patientSysId, online, appointmentDate, AppointmentStatus.PENDING);
            appointmentRepository.saveAndFlush(newAppointment);
            logger.info(String.format("Create an appointment: %s", newAppointment.toString()));
            return AppointmentStatus.PENDING;
        }
        return AppointmentStatus.ABORTED;
    }
}