package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WaitingListRequest {

    @NotNull
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    @NotNull
    private Long patientId;
}
