package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SpecializationResponse {

    private Long id;
    private String name;
    private String description;
}