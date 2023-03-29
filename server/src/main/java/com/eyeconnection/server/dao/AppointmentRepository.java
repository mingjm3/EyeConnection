package com.eyeconnection.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyeconnection.server.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
