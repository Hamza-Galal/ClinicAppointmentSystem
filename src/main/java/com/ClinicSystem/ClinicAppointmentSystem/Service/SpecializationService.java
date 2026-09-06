package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.SpecializationCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorResponse;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.SpecializationResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateSpecializationException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.SpecializationNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Specialization;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.SpecializationRepository;

@Service
public class SpecializationService {

    private final SpecializationRepository repo;
    private final DoctorRepository doctorRepository;

    public SpecializationService(
            SpecializationRepository repo,
            DoctorRepository doctorRepository) {
        this.repo = repo;
        this.doctorRepository = doctorRepository;
    }

    public SpecializationResponse createSpecialization(
            SpecializationCreateRequest request) {

        if (repo.findByName(request.getName()).isPresent()) {
            throw new DuplicateSpecializationException(
                    "Specialization Name Already exists");
        }

        Specialization specialization = new Specialization();
        specialization.setName(request.getName());
        specialization.setDescription(request.getDescription());

        repo.save(specialization);

        return convertToResponse(specialization);
    }

    public List<SpecializationResponse> getAllSpecializations() {
        return repo.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public SpecializationResponse getSpecializationById(Long id) {
        Specialization specialization = repo.findById(id)
                .orElseThrow(() ->
                        new SpecializationNotFoundException(
                                "Specialization Not Found"));

        return convertToResponse(specialization);
    }

    public List<DoctorResponse> getDoctorsBySpecialization(Long id) {
        if (!repo.existsById(id)) {
            throw new SpecializationNotFoundException(
                    "Specialization Not Found");
        }

        return doctorRepository.findBySpecializationId(id)
                .stream()
                .map(this::convertDoctorToResponse)
                .toList();
    }

    private SpecializationResponse convertToResponse(
            Specialization specialization) {

        return new SpecializationResponse(
                specialization.getId(),
                specialization.getName(),
                specialization.getDescription()
        );
    }

    private DoctorResponse convertDoctorToResponse(Doctor doctor) {
        Long specializationId = null;
        String specializationName = null;

        if (doctor.getSpecialization() != null) {
            specializationId = doctor.getSpecialization().getId();
            specializationName = doctor.getSpecialization().getName();
        }

        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                doctor.getLicenseNumber(),
                doctor.getYearsOfExperience(),
                doctor.getConsultationFee(),
                specializationId,
                specializationName
        );
    }
}