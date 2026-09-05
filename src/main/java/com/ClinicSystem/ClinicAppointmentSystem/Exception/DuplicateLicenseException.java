package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DuplicateLicenseException extends RuntimeException {

    public DuplicateLicenseException(String message) {
        super(message);
    }
}