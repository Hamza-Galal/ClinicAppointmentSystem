package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException(String message){
        super(message);
    }
}
