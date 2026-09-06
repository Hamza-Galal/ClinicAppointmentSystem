package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    Optional<Specialization> findByName(String name);
}