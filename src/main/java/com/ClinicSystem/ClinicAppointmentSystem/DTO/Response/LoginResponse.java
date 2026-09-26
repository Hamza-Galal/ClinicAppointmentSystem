package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor 
@Getter 
public class LoginResponse {
private  String token;
private  String email;
private  Role role;
}
