package com.ClinicSystem.ClinicAppointmentSystem.Exception;

public class WaitingListSlotAvailableException extends RuntimeException {

    public WaitingListSlotAvailableException(String message) {
        super(message);
    }
}
