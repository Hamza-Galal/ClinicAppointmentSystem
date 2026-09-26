package com.ClinicSystem.ClinicAppointmentSystem.Service;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.Exception.AccessDeniedException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;

@Service 
public class AuthorizationService {
    public void checkPatientOwnership(Patient patient , String email){
        if (!patient.getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to access this patient");       
     }
    }
}
