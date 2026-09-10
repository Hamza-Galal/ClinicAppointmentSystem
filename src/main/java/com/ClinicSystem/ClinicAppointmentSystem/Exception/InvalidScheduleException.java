package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class InvalidScheduleException extends RuntimeException {
    public InvalidScheduleException(String message){
        super(message);
    }
}
