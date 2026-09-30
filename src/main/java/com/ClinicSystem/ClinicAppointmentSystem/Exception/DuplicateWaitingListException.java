package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class DuplicateWaitingListException extends RuntimeException {

    public DuplicateWaitingListException(String message) {
        super(message);
    }
}
