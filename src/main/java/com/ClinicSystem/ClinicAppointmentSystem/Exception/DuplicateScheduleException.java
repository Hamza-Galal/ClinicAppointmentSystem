package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DuplicateScheduleException extends RuntimeException {
    public DuplicateScheduleException(String message){
        super(message);
    }
}
