package com.eyeconnection.server.entity;

import java.time.LocalDateTime;

import com.eyeconnection.server.enums.AppointmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {
    private @Id @Column(name = "appointment_id") @GeneratedValue Long appointmentId;
    private @Column(name = "doctor_sys_id") Long doctorSysId;
    private @Column(name = "patient_sys_id") Long patientSysId;
    private @Column(name = "appointment_status") String appointmentStatus;
    private Boolean online;
    private @Column(name = "online_meeting") String onlineMeeting;
    private @Column(name = "appointment_date") LocalDateTime appointmentDate;
    private String notes;

    public Appointment(Long doctorSysId, Long patientSysId, Boolean online, LocalDateTime appointmentDate, AppointmentStatus appointmentStatus) {
        this.doctorSysId = doctorSysId;
        this.patientSysId = patientSysId;
        this.online = online;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus.toString();
    }

    @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", doctorSysId=" + doctorSysId + ", patientSysId="
                + patientSysId + ", appointmentStatus=" + appointmentStatus + ", online=" + online + ", onlineMeeting="
                + onlineMeeting + ", appointmentDate=" + appointmentDate + ", notes=" + notes + "]";
    }
}
