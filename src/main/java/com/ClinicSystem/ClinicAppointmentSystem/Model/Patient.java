package com.ClinicSystem.ClinicAppointmentSystem.Model;
import java.time.LocalDate;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String LastName;
    
    @Column(unique = true)
    private String email;

    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private LocalDate registrationDate;

   
    
}
