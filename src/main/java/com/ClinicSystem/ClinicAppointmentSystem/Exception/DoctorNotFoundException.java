package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String message) {
        super(message);
    }
}