package com.ClinicSystem.ClinicAppointmentSystem.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PatientCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.PatientResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateEmailException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Gender;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;


@ExtendWith (MockitoExtension.class)
public class PatientServiceTest {
    @Mock PatientRepository patientRepository;
    @Mock AuthorizationService authorizationService;
    @InjectMocks PatientService service;
    
    @Test 
    void registerPatientSuccessfully(){
        PatientCreateRequest request = new PatientCreateRequest("Hamza", "Galal", "Hamza@gmail.com","01022568547", LocalDate.of(2006, 5, 26),Gender.MALE);
        when(patientRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
                Patient patient = invocation.getArgument(0);
                patient.setId(1L);
                return patient;
        }
        );
        PatientResponse response = service.createPatient(request);
        assertEquals(1L, response.getId());
        assertEquals("Hamza@gmail.com", response.getEmail());
        assertNotNull(response.getRegistrationDate());
        verify(patientRepository).save(any(Patient.class));
    }
    @Test 
    void rejectsDuplicatePatientEmail(){
        PatientCreateRequest request = new PatientCreateRequest("Hamza", "Galal", "Hamza@gmail.com","01022568547", LocalDate.of(2006, 5, 26),Gender.MALE);
        when(patientRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Patient()));
        assertThrows(DuplicateEmailException.class,() -> service.createPatient(request));
        verify(patientRepository , never()).save(any(Patient.class));
    }
}
