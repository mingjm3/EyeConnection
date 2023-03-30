package com.eyeconnection.server.entity;

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
    @Column(name = "support_online")
    private boolean supportOnline;

    private String email;
    private String password;
    private Float rate;
    private String address;
    private String city;
    private String state;
    private String role;
    private String portrait;

    public Doctor() {
    }

    public Doctor(Long sysId, String lastName, String firstName, String zipCode,
            boolean supportOnline, String email, String password, Float rate, String address, String city, String state,
            String role, String portrait) {
        this.sysId = sysId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.zipCode = zipCode;
        this.supportOnline = supportOnline;
        this.email = email;
        this.password = password;
        this.rate = rate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.role = role;
        this.portrait = portrait;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Doctor [sysId=" + sysId + ", lastName=" + lastName + ", firstName=" + firstName + ", zipCode=" + zipCode + ", supportOnline=" + supportOnline
                + ", email=" + email + ", rate=" + rate + ", address=" + address + ", city=" + city + ", state=" + state
                + ", role=" + role + ", portrait=" + portrait + "]";
    }
}
