package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class WaitingListEntryNotFoundException extends RuntimeException {

    public WaitingListEntryNotFoundException(String message) {
        super(message);
    }
}
