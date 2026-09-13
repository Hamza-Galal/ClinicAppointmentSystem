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

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorUpdateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AppointmentService;
import com.ClinicSystem.ClinicAppointmentSystem.Service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public DoctorController(
            DoctorService doctorService,
            AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(
            @Valid @RequestBody DoctorCreateRequest request) {

        DoctorResponse response = doctorService.createDoctor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments(
            @PathVariable Long doctorId,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                appointmentService.getDoctorAppointments(
                        doctorId,
                        status,
                        date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(id, request));
    }

    @PutMapping("/{doctorId}/specialization/{specializationId}")
    public ResponseEntity<DoctorResponse> assignSpecialization(
            @PathVariable Long doctorId,
            @PathVariable Long specializationId) {

        return ResponseEntity.ok(
                doctorService.assignSpecialization(
                        doctorId,
                        specializationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DoctorResponse> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.noContent().build();
    }
}