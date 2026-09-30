package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.WaitingListRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.WaitingListResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.WaitingListService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/doctors/{doctorId}/waiting-list")
@AllArgsConstructor
public class WaitingListController {

    private final WaitingListService waitingListService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<WaitingListResponse> joinWaitingList(
            @PathVariable Long doctorId,
            @Valid @RequestBody WaitingListRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(waitingListService.joinWaitingList(doctorId, request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    public ResponseEntity<List<WaitingListResponse>> getWaitingList(
            @PathVariable Long doctorId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time) {

        return ResponseEntity.ok(
                waitingListService.getWaitingList(doctorId, date, time));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> leaveWaitingList(
            @PathVariable Long doctorId,
            @RequestParam Long patientId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time) {

        waitingListService.leaveWaitingList(
                doctorId,
                patientId,
                date,
                time);

        return ResponseEntity.noContent().build();
    }
}
