package com.ClinicSystem.ClinicAppointmentSystem.Service;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;



@ExtendWith (MockitoExtension.class)
public class AppointmentSlotServiceTest {
    @Mock DoctorRepository doctorRepository;
    @Mock DoctorScheduleRepository doctorScheduleRepository;
    @Mock AppointmentRepository appointmentRepository;
    @InjectMocks AppointmentSlotService service;

    @Test 
    void cancelledAppointmentSlotsBecomesAvailableAgain(){
        Long doctorId = 2L;
        LocalDate date = LocalDate.now().plusDays(10);
        LocalTime cancelledTime = LocalTime.of(10, 0);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(date.getDayOfWeek());
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(11, 0));
        Appointment cancelledAppointment = new Appointment(7L , new Patient() ,doctor , date , cancelledTime ,"Checkup",AppointmentStatus.CANCELLED );
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.findByDoctorId(doctorId)).thenReturn(List.of(schedule));
        when(appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date)).thenReturn(List.of(cancelledAppointment));
        var availableSlots = service.getAvailableSlots(doctorId, date);
        assertTrue(availableSlots.stream().anyMatch(slot -> slot.getTime().equals(cancelledTime)));
    }
}
