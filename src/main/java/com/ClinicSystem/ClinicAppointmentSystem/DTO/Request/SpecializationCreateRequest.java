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
public class SpecializationCreateRequest {

    @NotBlank(message = "Specialization name is required")
    private String name;

    @NotBlank(message = "Specialization description is required")
    private String description;
}