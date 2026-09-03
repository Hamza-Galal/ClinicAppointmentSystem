package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message){
        super(message);
    }
}
