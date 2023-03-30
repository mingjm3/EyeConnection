package com.eyeconnection.server.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(Long id) {
        super("Appointment not found " + id); 
    }
}
