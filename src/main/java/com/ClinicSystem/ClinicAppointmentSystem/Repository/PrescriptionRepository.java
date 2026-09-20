package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    List<Prescription> findByMedicalRecordId(long medicalRecordId);
    List<Prescription> findByMedicalRecordPatientId(long patientId);
}
