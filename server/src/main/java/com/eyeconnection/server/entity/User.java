package com.eyeconnection.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Column(name = "sys_id")
    private @Id @GeneratedValue Long sysId;

    private String email;
    private String password;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "zip_code")
    private String zipCode;

    public User() {}

    public User(Long sysId, String email, String password, String firstName, String lastName, String zipCode) {
        this.sysId = sysId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }
}
