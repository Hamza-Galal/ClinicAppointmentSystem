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

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }
    
    //createPatient//
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

    //Get All Patients//
    public List<PatientResponse> getAllPatients(){
        return repo.findAll().stream().map(patient ->this.convertToResponse(patient)).toList();
    }

    //Get Patient by id//
    public PatientResponse getPatientById(Long id){
        Patient p = repo.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient Not Found"));
        return convertToResponse(p);
    }

    //update patient
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
    
    //DeletePatient//
    public void deletePatient(Long id){
        if (!repo.existsById(id)) {
            throw new PatientNotFoundException("Patient Not Found");
        }
        repo.deleteById(id);
    }

    /* A function to convert Patient object to a Patient Response object */
    public PatientResponse convertToResponse(Patient p){
        return new PatientResponse(p.getId(),p.getFirstName(),
        p.getLastName(),p.getEmail(),
        p.getPhoneNumber(),p.getDateOfBirth(),
        p.getGender(),p.getRegistrationDate());
    }
}
