package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
public class AppointmentCreateRequest {
@NotNull(message = "Patient ID is required")
private Long patientId;

@NotNull(message = "Doctor ID is required")
private Long doctorId;

@NotNull(message = "Appointment Date is required")
private LocalDate appointmentDate;

@NotNull(message = "Appointment time is required")
private LocalTime appointmentTime;

@NotBlank(message = "Reason for visit is required")
private String reasonForVisit;
}
