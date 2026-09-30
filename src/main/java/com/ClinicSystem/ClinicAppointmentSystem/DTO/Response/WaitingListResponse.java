package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WaitingListResponse {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDateTime joinedAt;
    private boolean firstEligible;
}
