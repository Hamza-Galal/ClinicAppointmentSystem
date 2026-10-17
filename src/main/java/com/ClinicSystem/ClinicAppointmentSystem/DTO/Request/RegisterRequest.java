package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import java.time.LocalDate;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Gender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
@Getter 
@Setter 
@Valid 
public class RegisterRequest {
    @NotBlank (message = "Email required.")
    private String email;

    @NotBlank (message = "Password is required.")
    private String password;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotNull (message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;
}
