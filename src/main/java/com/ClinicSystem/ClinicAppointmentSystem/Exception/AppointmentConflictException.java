package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class AppointmentConflictException extends RuntimeException {
    public AppointmentConflictException(String message){
        super(message);
    }
}
