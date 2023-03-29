package com.eyeconnection.server.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyeconnection.server.entity.AvailableDates;

@Repository
public interface AvailableDatesRepository extends JpaRepository<AvailableDates, Long>{
    public AvailableDates findByDoctorSysIdAndAvailableDate(Long doctorSysId, LocalDateTime availableDate);
}
