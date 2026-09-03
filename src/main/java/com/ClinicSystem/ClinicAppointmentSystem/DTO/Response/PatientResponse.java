package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;
import java.time.LocalDate;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PatientResponse {
    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private LocalDate registrationDate;
}
