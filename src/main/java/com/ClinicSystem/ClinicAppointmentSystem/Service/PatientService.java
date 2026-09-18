package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PatientCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PatientUpdateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.PatientResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateEmailException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.PatientNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }
    

    public PatientResponse createPatient(PatientCreateRequest request){
        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email Already exists");

        }
        Patient p = new Patient();
        p.setFirstName(request.getFirstName());
        p.setLastName(request.getLastName());
        p.setEmail(request.getEmail());
        p.setPhoneNumber(request.getPhoneNumber());
        p.setDateOfBirth(request.getDateOfBirth());
        p.setGender(request.getGender());
        p.setRegistrationDate(LocalDate.now());
        repo.save(p);
        return convertToResponse(p);
    }


    public List<PatientResponse> getAllPatients(){
        return repo.findAll().stream().map(patient ->this.convertToResponse(patient)).toList();
    }


    public PatientResponse getPatientById(Long id){
        Patient p = repo.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient Not Found"));
        return convertToResponse(p);
    }


    public PatientResponse updatePatient(Long id, PatientUpdateRequest request){
        Patient p = repo.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient Not Found"));

        if (!p.getEmail().equals(request.getEmail()) && repo.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email Already exists");
        }
        p.setFirstName(request.getFirstName());
        p.setLastName(request.getLastName());
        p.setEmail(request.getEmail());
        p.setPhoneNumber(request.getPhoneNumber());
        p.setDateOfBirth(request.getDateOfBirth());
        p.setGender(request.getGender());
        repo.save(p);
        return convertToResponse(p);
    }
    

    public void deletePatient(Long id){
        if (!repo.existsById(id)) {
            throw new PatientNotFoundException("Patient Not Found");
        }
        repo.deleteById(id);
    }


    public PatientResponse convertToResponse(Patient p){
        return new PatientResponse(p.getId(),p.getFirstName(),
        p.getLastName(),p.getEmail(),
        p.getPhoneNumber(),p.getDateOfBirth(),
        p.getGender(),p.getRegistrationDate());
    }

    public Page<PatientResponse> getPatients(
            int page,
            int size,
            String sort) {

        PageRequest pageable;

        if (sort == null || sort.isBlank()) {
            pageable = PageRequest.of(
                    page,
                    size,
                    Sort.by("registrationDate").ascending());
        } else {
            String[] sortParts = sort.split(",");

            Sort.Direction direction = Sort.Direction.ASC;

            if (sortParts.length > 1) {
                direction = Sort.Direction.fromString(sortParts[1]);
            }

            pageable = PageRequest.of(
                    page,
                    size,
                    Sort.by(direction, sortParts[0]));
        }

        return repo.findAll(pageable)
                .map(this::convertToResponse);
    }
}
