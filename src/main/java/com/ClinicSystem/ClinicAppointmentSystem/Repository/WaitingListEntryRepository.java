package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.WaitingListEntry;

public interface WaitingListEntryRepository extends JpaRepository<WaitingListEntry, Long> {

    boolean existsByPatientIdAndDoctorIdAndAppointmentDateAndAppointmentTime(
            Long patientId,
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime);

    Optional<WaitingListEntry> findByPatientIdAndDoctorIdAndAppointmentDateAndAppointmentTime(
            Long patientId,
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime);

    List<WaitingListEntry> findByDoctorIdAndAppointmentDateAndAppointmentTimeOrderByJoinedAtAsc(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime);
}
