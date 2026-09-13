package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PatientCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PatientUpdateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.PatientResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AppointmentService;
import com.ClinicSystem.ClinicAppointmentSystem.Service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private PatientService patientService;
    private AppointmentService appointmentService;

    public PatientController(
            PatientService patientService,
            AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody PatientCreateRequest request) {

        PatientResponse response = patientService.createPatient(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(
            @Valid @PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getPatientAppointments(
            @PathVariable Long patientId,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                appointmentService.getPatientAppointments(
                        patientId,
                        status,
                        date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> UpdatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateRequest request) {

        return ResponseEntity.ok(
                patientService.updatePatient(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientResponse> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}