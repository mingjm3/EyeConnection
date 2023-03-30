package com.eyeconnection.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyeconnection.server.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    public Doctor findByEmail(String email);
}
