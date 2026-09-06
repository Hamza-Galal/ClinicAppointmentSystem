package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findBySpecializationId(Long specializationId);
}