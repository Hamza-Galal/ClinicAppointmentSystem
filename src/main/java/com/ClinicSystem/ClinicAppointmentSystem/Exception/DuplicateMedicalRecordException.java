package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DuplicateMedicalRecordException extends RuntimeException {

    public DuplicateMedicalRecordException(String message) {
        super(message);
    }
}