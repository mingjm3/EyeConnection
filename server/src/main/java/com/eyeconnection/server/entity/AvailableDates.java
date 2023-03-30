package com.eyeconnection.server.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "available_dates")
public class AvailableDates {
    private @Id @GeneratedValue Long id;
    private @Column(name = "doctor_sys_id") Long doctorSysId;
    private @Column(name = "available_date") LocalDateTime availableDate;
    
    public AvailableDates() {
    }

    public AvailableDates(Long doctorSysId, LocalDateTime availableDate) {
        this.doctorSysId = doctorSysId;
        this.availableDate = availableDate;
    }

    @Override
    public String toString() {
        return "AvailableDates [doctorSysId=" + doctorSysId + ", availableDate=" + availableDate + "]";
    }
}
