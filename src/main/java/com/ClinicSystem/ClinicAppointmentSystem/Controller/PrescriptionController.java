package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PrescriptionCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.PrescriptionResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.PrescriptionService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api")
public class PrescriptionController {
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }
    
    @PostMapping("/medical-records/{recordId}/prescriptions")
    public ResponseEntity<PrescriptionResponse> createPrescription(@PathVariable Long recordId ,@Valid @RequestBody PrescriptionCreateRequest request){
        return ResponseEntity.ok(service.createPrescription(recordId, request));
    }
    @GetMapping("/patients/{patientId}/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPatient(@PathVariable Long patientId){
        return ResponseEntity.ok(service.getPrescriptionsByPatient(patientId));
    }
    @GetMapping("/medical-records/{recordId}/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByMedicalRecord(@PathVariable Long recordId){
        return ResponseEntity.ok(service.getPrescriptionsByRecord(recordId));
    }
}
