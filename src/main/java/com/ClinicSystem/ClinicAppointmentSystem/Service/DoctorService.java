package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorUpdateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateLicenseException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.SpecializationNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Specialization;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.SpecializationRepository;

@Service
public class DoctorService {

    private final DoctorRepository repo;
    private final SpecializationRepository specializationRepository;

    public DoctorService(
            DoctorRepository repo,
            SpecializationRepository specializationRepository) {
        this.repo = repo;
        this.specializationRepository = specializationRepository;
    }

    public DoctorResponse createDoctor(DoctorCreateRequest request) {
        if (repo.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            throw new DuplicateLicenseException("License Number Already exists");
        }

        Specialization specialization = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> new SpecializationNotFoundException("Specialization Not Found"));

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setSpecialization(specialization);

        repo.save(doctor);

        return convertToResponse(doctor);
    }

    public List<DoctorResponse> getAllDoctors() {
        return repo.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = repo.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        return convertToResponse(doctor);
    }

    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor doctor = repo.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        if (!doctor.getLicenseNumber().equals(request.getLicenseNumber())
                && repo.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            throw new DuplicateLicenseException("License Number Already exists");
        }

        Specialization specialization = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> new SpecializationNotFoundException("Specialization Not Found"));

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setSpecialization(specialization);

        repo.save(doctor);

        return convertToResponse(doctor);
    }

    public void deleteDoctor(Long id) {
        if (!repo.existsById(id)) {
            throw new DoctorNotFoundException("Doctor Not Found");
        }

        repo.deleteById(id);
    }

    public DoctorResponse assignSpecialization(Long doctorId, Long specializationId) {
        Doctor doctor = repo.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new SpecializationNotFoundException("Specialization Not Found"));

        doctor.setSpecialization(specialization);
        repo.save(doctor);

        return convertToResponse(doctor);
    }

    public DoctorResponse convertToResponse(Doctor doctor) {
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