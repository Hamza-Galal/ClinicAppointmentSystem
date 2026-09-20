package com.ClinicSystem.ClinicAppointmentSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity 
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
public class Prescription {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private  String medicationName;
    private  String dosage;
    private  String frequency;
    private  String treatmentDuration;
    private  String additionalInstructions;

    @ManyToOne 
    @JoinColumn (name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;
}
