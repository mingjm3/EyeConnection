package com.eyeconnection.server.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AppointmentRepo;
import com.eyeconnection.server.dao.AvailableDatesRepo;
import com.eyeconnection.server.dao.UserRepo;
import com.eyeconnection.server.entity.Appointment;
import com.eyeconnection.server.entity.AvailableDates;
import com.eyeconnection.server.entity.User;
import com.eyeconnection.server.enums.AppointmentStatus;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private AvailableDatesRepo availableDatesRepo;
    private AppointmentRepo appointmentRepo;
    private UserRepo userRepo;

    public UserService(AvailableDatesRepo availableDatesRepo, AppointmentRepo appointmentRepo, UserRepo userRepo) {
        this.availableDatesRepo = availableDatesRepo;
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }

    public User signUp(User newUser) {
        User findResult = userRepo.findByEmail(newUser.getEmail());
        if(findResult != null) {
            logger.warn(String.format("User sign up failed: %s", newUser.toString()));
            return null;
        }
        User savedUser = userRepo.save(newUser);
        logger.info(String.format("User sign up successfully: %s", savedUser.toString()));
        return savedUser;
    }

    public boolean userLogIn(String email, String password) {
        User findResult = userRepo.findByEmail(email);
        return findResult != null && findResult.getPassword().equals(password);
    }

    public AppointmentStatus makeAppointment(Long patientSysId, Long doctorSysId, LocalDateTime appointmentDate, Boolean online) {
        AvailableDates queryResult = availableDatesRepo.findByDoctorSysIdAndAvailableDate(doctorSysId, appointmentDate);
        if(queryResult != null) {
            Appointment newAppointment = new Appointment(doctorSysId, patientSysId, online, appointmentDate, AppointmentStatus.PENDING);
            appointmentRepo.saveAndFlush(newAppointment);
            logger.info(String.format("Create an appointment: %s", newAppointment.toString()));
            availableDatesRepo.deleteByDoctorSysIdAndAvailableDate(doctorSysId, appointmentDate);
            logger.info(String.format("Delete doctor [%s] available date [%s]", doctorSysId, appointmentDate));
            return AppointmentStatus.PENDING;
        }
        logger.warn(String.format("Create an appointment failed with doctor [%s] available date [%s]", doctorSysId, appointmentDate));
        return AppointmentStatus.ABORTED;
    }
}