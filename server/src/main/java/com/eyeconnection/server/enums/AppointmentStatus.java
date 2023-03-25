package com.eyeconnection.server.enums;

public enum AppointmentStatus {
    //Patient make an appointment and wait for doctor's confirmation
    PENDING,
    //Doctor have confirmed the appointment
    APPROVED,
    //Appointment have finished
    FINISHED,
    //Appointmnet have been cancelled by patients or doctors
    CANCELLED,
    //Appointmnet have error or make failed
    ABORTED;
}
