package com.ClinicSystem.ClinicAppointmentSystem.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.MedicalRecordCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateMedicalRecordException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.InvalidMedicalRecordException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.MedicalRecord;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.MedicalRecordRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;

@ExtendWith (MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock MedicalRecordRepository medicalRecordRepository;
    @Mock AppointmentRepository appointmentRepository;
    @Mock PatientRepository patientRepository;
    @InjectMocks MedicalRecordService service;

    @Test 
    void rejectsMedicalRecordForAppointmentNotCompleted(){
        Patient patient = new Patient();
        patient.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        Appointment appointment = new Appointment(3L , patient , doctor , LocalDate.now().plusDays(1),LocalTime.of(10, 0) ,"Checkup", AppointmentStatus.CONFIRMED);
        when(appointmentRepository.findById(3L)).thenReturn(Optional.of(appointment));
        assertThrows(InvalidMedicalRecordException.class, ()-> service.createMedicalRecord(3L, new MedicalRecordCreateRequest("Treatment Given")));
        verify(medicalRecordRepository, never()).save(any(MedicalRecord.class));
    }
    @Test 
    void rejectsSecondMedicalRecordForSameAppointment(){
        Patient patient = new Patient();
        patient.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        Appointment appointment = new Appointment(3L , patient , doctor , LocalDate.now().plusDays(1),LocalTime.of(10, 0) ,"Checkup", AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(3L)).thenReturn(Optional.of(appointment));
        when(medicalRecordRepository.existsByAppointmentId(3L)).thenReturn(true);
        assertThrows(DuplicateMedicalRecordException.class, ()-> service.createMedicalRecord(3L, new MedicalRecordCreateRequest("Treatment Given")));
        verify(medicalRecordRepository,never()).save(any(MedicalRecord.class));
    }
}
