package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class MedicalRecordNotFoundException extends RuntimeException {

    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}