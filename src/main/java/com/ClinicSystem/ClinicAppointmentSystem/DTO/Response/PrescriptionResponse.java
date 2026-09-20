package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
public class PrescriptionResponse {
    private Long id;
    private Long medicalRecordId;
    private Long PatientId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String treatmentDuration;
    private String additionalInstructions;
}
