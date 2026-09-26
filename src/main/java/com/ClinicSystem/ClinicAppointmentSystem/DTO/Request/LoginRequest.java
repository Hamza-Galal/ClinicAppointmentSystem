package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Valid 
@Getter 
@Setter 
public class LoginRequest {
    @NotBlank (message = "Email required.")
    private String email;
    @NotBlank (message = "Password is required.")
    private String password;    
}
