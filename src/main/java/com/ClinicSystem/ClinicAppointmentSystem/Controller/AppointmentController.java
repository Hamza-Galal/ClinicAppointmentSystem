package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.AppointmentCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentCreateRequest request) {

        AppointmentResponse response = service.createAppointment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAppointments(
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(service.getAppointments(status, date));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentsById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getAppointmentById(id));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<AppointmentResponse> confirmAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.confirmAppointment(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.cancelAppointment(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponse> completeAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.completeAppointment(id));
    }

    @PatchMapping("/{id}/no-show")
    public ResponseEntity<AppointmentResponse> markAppointmentAsNoShow(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.markAppointmentAsNoShow(id));
    }
}