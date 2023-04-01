package com.eyeconnection.server.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyeconnection.server.entity.AvailableDates;

import jakarta.transaction.Transactional;

public interface AvailableDatesRepo extends JpaRepository<AvailableDates, Long>{
    public AvailableDates findByDoctorSysIdAndAvailableDate(Long doctorSysId, LocalDateTime availableDate);

    @Transactional
    public void deleteByDoctorSysId(Long doctorSysId);

    @Transactional
    public void deleteByDoctorSysIdAndAvailableDate(Long doctorSysId, LocalDateTime availableDate);
}
