package com.ClinicSystem.ClinicAppointmentSystem.Model;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
@Entity 
public class Appointment {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne 
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne 
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    private LocalDate appointmentDate;
    
    private LocalTime appointmentTime;

    private String reasonForVisit;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    
}
