package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.MedicalRecordCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.MedicalRecordResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.MedicalRecordService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping("/appointments/{appointmentId}/medical-record")
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(
            @PathVariable Long appointmentId,
            @Valid @RequestBody MedicalRecordCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medicalRecordService.createMedicalRecord(
                        appointmentId,
                        request));
    }

    @GetMapping("/patients/{patientId}/medical-records")
    public ResponseEntity<List<MedicalRecordResponse>> getPatientMedicalRecords(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                medicalRecordService.getPatientMedicalRecords(patientId));
    }

    @GetMapping("/medical-records/{id}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                medicalRecordService.getMedicalRecordById(id));
    }
}