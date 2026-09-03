package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message){
        super(message);
    }
}
