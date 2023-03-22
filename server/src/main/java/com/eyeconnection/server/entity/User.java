package com.eyeconnection.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    private @Id @GeneratedValue Long sysId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String zipCode;

    public User(String email, String password, String firstName, String lastName, String zipCode) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }
}
