package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import java.time.LocalDate;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientCreateRequest {

    

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;
    
    @NotBlank(message = "Email Address is required")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    


}
