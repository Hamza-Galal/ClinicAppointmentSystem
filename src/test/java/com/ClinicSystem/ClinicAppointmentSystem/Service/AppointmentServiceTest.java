package com.ClinicSystem.ClinicAppointmentSystem.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.AppointmentCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.AppointmentConflictException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.InvalidAppointmentStatusException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.OutsideWorkingHoursException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;

@ExtendWith (MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock AppointmentRepository appointmentRepository;
    @Mock DoctorScheduleRepository doctorScheduleRepository;
    @Mock PatientRepository patientRepository;
    @Mock DoctorRepository doctorRepository;
    @Mock WaitingListService waitingListService;
    @InjectMocks AppointmentService service;
    Patient patient;
    Doctor doctor;
    LocalDate date;
    LocalTime time;

    private AppointmentCreateRequest request (long patientId , long doctorId , LocalTime appointmentTime){
        return new AppointmentCreateRequest(patientId,doctorId,date,appointmentTime,"Checkup");
    }
    @Test 
    void booksAppointmentSuccessfully(){
        patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(date.getDayOfWeek());
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(17, 0));
        when(doctorScheduleRepository.findByDoctorId(2L)).thenReturn(List.of(schedule));
        when(appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(2L, date, time, AppointmentStatus.CANCELLED)).thenReturn(false);
        when(appointmentRepository.existsByPatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(1L, date, time, AppointmentStatus.CANCELLED)).thenReturn(false);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment a = invocation.getArgument(0);
            a.setId(9L);
            return  a;
        });
        AppointmentResponse response = service.createAppointment(request(1L, 2L, time));
        assertEquals(9L, response.getId());
        assertEquals(AppointmentStatus.SCHEDULED, response.getStatus());
    }
    @Test 
    void rejectsDoctorDoubleBooking(){
        patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        DoctorSchedule schedule = new DoctorSchedule();
         schedule.setDoctor(doctor);
         schedule.setDayOfWeek(date.getDayOfWeek());
         schedule.setStartTime(LocalTime.of(9, 0));
         schedule.setEndTime(LocalTime.of(17, 0));
         when(doctorScheduleRepository.findByDoctorId(2L)).thenReturn(List.of(schedule));
         when(appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(2L, date, time, AppointmentStatus.CANCELLED)).thenReturn(false);
         when(appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(2L, date, time, AppointmentStatus.CANCELLED)).thenReturn(true);
        assertThrows(AppointmentConflictException.class,()-> service.createAppointment(request(1L, 2L, time)));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
    @Test 
    void rejectsPatientDoubleBooking(){
        patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(date.getDayOfWeek());
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(17, 0));
        when(doctorScheduleRepository.findByDoctorId(2L)).thenReturn(List.of(schedule));
        when(appointmentRepository.existsByPatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(1L, date, time, AppointmentStatus.CANCELLED)).thenReturn(false);
     when(appointmentRepository.existsByPatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(1L, date, time, AppointmentStatus.CANCELLED)).thenReturn(true);
     assertThrows(AppointmentConflictException.class,()-> service.createAppointment(request(1L, 2L, time)));
     verify(appointmentRepository, never()).save(any(Appointment.class));
    }
    @Test 
    void rejectsAppointmentOutsideWorkingHours(){
           patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(date.getDayOfWeek());
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(17, 0));
        when(doctorScheduleRepository.findByDoctorId(2L)).thenReturn(List.of(schedule));
        assertThrows(OutsideWorkingHoursException.class, ()->service.createAppointment(request(1L, 2L, LocalTime.of(18, 0))));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
    @Test 
    void cancelsAppointment(){
        patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
        Appointment appointment = new Appointment(5L , patient , doctor,date ,time , "Checkup", AppointmentStatus.SCHEDULED);
        when(appointmentRepository.findById(5L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        AppointmentResponse response = service.cancelAppointment(5L);
        assertEquals(AppointmentStatus.CANCELLED, response.getStatus());
        verify(waitingListService).getFirstEligiblePatient(2L, date, time);
    }
    @Test 
    void rejectsInvalidStatusTransitionAfterCancellation(){
        patient = new Patient();
        patient.setId(1L);
        doctor = new Doctor();
        doctor.setId(2L);
        date = LocalDate.now().plusDays(10);
        time = LocalTime.of(10, 0);
     Appointment appointment = new Appointment(5L , patient , doctor,date ,time , "Checkup", AppointmentStatus.CANCELLED);
     when(appointmentRepository.findById(5L)).thenReturn(Optional.of(appointment));
     assertThrows(InvalidAppointmentStatusException.class, ()-> service.confirmAppointment(5L));
     verify(appointmentRepository, never()).save(any(Appointment.class));


    }
}
