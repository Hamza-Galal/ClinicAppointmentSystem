package com.ClinicSystem.ClinicAppointmentSystem.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
@NoArgsConstructor
@AllArgsConstructor 
@Getter 
@Setter 
@Entity 
public class DoctorSchedule {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne 
    @JoinColumn 
    private Doctor doctor;
    
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    
    private LocalTime endTime;


}
