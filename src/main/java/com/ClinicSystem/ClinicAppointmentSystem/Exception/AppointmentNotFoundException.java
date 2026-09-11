package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message){
        super(message);
    }
}
