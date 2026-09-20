package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Valid 
@Getter 
@Setter 
public class PrescriptionCreateRequest {
    @NotBlank (message = "Medication Name is required")
    private String medicationName;

    @NotBlank (message = "Dosage is required")
    private String dosage;

    @NotBlank (message = "Frequency is required")
    private String frequency;

    @NotBlank (message = "Treatment Duration is required")
    private String treatmentDuration;

    private  String additionalInstructions;
}
