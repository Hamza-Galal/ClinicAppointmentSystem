package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
public class AppointmentResponse {
    private  Long id;
    private  Long patientId;
    private  Long doctorId;
    private LocalDate appointmenDate;
    private LocalTime appointmentTime;
    private String reasonForVisit;
    private AppointmentStatus status;
}
