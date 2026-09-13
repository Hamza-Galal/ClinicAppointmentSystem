package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status);

    boolean existsBypatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Long patientId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status);

    @Query("""
            SELECT a
            FROM Appointment a
            WHERE (:status IS NULL OR a.status = :status)
            AND (:appointmentDate IS NULL OR a.appointmentDate = :appointmentDate)
            ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
            """)
    List<Appointment> findAppointments(
            @Param("status") AppointmentStatus status,
            @Param("appointmentDate") LocalDate appointmentDate);

    @Query("""
            SELECT a
            FROM Appointment a
            WHERE a.patient.id = :patientId
            AND (:status IS NULL OR a.status = :status)
            AND (:appointmentDate IS NULL OR a.appointmentDate = :appointmentDate)
            ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
            """)
    List<Appointment> findPatientAppointments(
            @Param("patientId") Long patientId,
            @Param("status") AppointmentStatus status,
            @Param("appointmentDate") LocalDate appointmentDate);

    @Query("""
            SELECT a
            FROM Appointment a
            WHERE a.doctor.id = :doctorId
            AND (:status IS NULL OR a.status = :status)
            AND (:appointmentDate IS NULL OR a.appointmentDate = :appointmentDate)
            ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
            """)
    List<Appointment> findDoctorAppointments(
            @Param("doctorId") Long doctorId,
            @Param("status") AppointmentStatus status,
            @Param("appointmentDate") LocalDate appointmentDate);
}