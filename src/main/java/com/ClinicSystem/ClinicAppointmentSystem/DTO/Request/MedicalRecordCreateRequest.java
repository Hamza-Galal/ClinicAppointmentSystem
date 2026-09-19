package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecordCreateRequest {

    @NotBlank(message = "Outcome is required")
    private String outcome;
}