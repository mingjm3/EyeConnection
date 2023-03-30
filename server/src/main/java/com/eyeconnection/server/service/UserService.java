package com.eyeconnection.server.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eyeconnection.server.dao.AppointmentRepository;
import com.eyeconnection.server.dao.AvailableDatesRepository;
import com.eyeconnection.server.dao.UserRepository;
import com.eyeconnection.server.entity.Appointment;
import com.eyeconnection.server.entity.AvailableDates;
import com.eyeconnection.server.entity.User;
import com.eyeconnection.server.enums.AppointmentStatus;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private AvailableDatesRepository availableDatesRepository;
    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;

    public UserService(AvailableDatesRepository availableDatesRepository, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.availableDatesRepository = availableDatesRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public User signUp(User newUser) {
        User findResult = userRepository.findByEmail(newUser.getEmail());
        
        //check if user is already exists
        if(findResult != null) {
            logger.warn(String.format("User sign up failed: %s", newUser.toString()));
            return null;
        }
        
        User savedUser = userRepository.save(newUser);
        logger.info(String.format("User sign up successfully: %s", savedUser.toString()));
        return savedUser;
    }

    public boolean userLogIn(String email, String password) {
        User findResult = userRepository.findByEmail(email);
        
        //check if user is existing and password is correct
        if(findResult == null || !findResult.getPassword().equals(password)) {
            logger.warn(String.format("User log in failed: [%s] ", email));
            return false;
        }

        logger.info(String.format("User log in successfully: %s", findResult.toString()));
        return true;
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