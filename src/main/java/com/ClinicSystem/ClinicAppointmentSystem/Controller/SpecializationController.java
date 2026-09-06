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

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.SpecializationCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorResponse;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.SpecializationResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.SpecializationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(
            SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping
    public ResponseEntity<SpecializationResponse> createSpecialization(
            @Valid @RequestBody SpecializationCreateRequest request) {

        SpecializationResponse response =
                specializationService.createSpecialization(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SpecializationResponse>>
    getAllSpecializations() {

        return ResponseEntity.ok(
                specializationService.getAllSpecializations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponse>
    getSpecializationById(@PathVariable Long id) {

        return ResponseEntity.ok(
                specializationService.getSpecializationById(id));
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorResponse>>
    getDoctorsBySpecialization(@PathVariable Long id) {

        return ResponseEntity.ok(
                specializationService.getDoctorsBySpecialization(id));
    }
}