package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(Long doctorId , LocalDate appointmentDate , LocalTime appointmentTime , AppointmentStatus status);
    boolean existsBypatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(Long patientId , LocalDate appointmentDate , LocalTime appointmentTime , AppointmentStatus status);

}
