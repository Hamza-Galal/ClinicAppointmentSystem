package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class InvalidAppointmentStatusException extends RuntimeException {
    public InvalidAppointmentStatusException(String message) {
        super(message);
    }
}