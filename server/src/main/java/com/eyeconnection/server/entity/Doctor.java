package com.eyeconnection.server.entity;

import java.util.Date;

import com.eyeconnection.server.enums.DoctorRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Column(name = "sys_id")
    private @Id @GeneratedValue Long sysId;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "available_dates")
    private Date[] availableDates;
    @Column(name = "support_online")
    private boolean supportOnline;

    private String email;
    private String password;
    private Float rate;
    private String address;
    private String city;
    private String state;
    private DoctorRoles role;
    private String portrait;
}
