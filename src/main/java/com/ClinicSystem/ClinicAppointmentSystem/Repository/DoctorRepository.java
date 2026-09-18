package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findBySpecializationId(Long specializationId);

    @Query("""
            SELECT d
            FROM Doctor d
            WHERE (:specialization IS NULL
                   OR LOWER(d.specialization.name) LIKE LOWER(CONCAT('%', :specialization, '%')))
            AND (:name IS NULL
                 OR LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
                 OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
            """)
    Page<Doctor> searchDoctors(
            @Param("specialization") String specialization,
            @Param("name") String name,
            Pageable pageable);
}