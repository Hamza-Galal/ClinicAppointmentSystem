package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class OutsideWorkingHoursException extends RuntimeException {
    public OutsideWorkingHoursException(String message){
        super(message);
    }
}
