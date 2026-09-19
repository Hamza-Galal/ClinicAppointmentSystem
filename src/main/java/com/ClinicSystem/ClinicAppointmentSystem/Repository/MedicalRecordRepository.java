package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    boolean existsByAppointmentId(Long appointmentId);

    List<MedicalRecord> findByAppointmentPatientId(Long patientId);
}