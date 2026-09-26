package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.LoginRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.RegisterRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.LoginResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AuthenticationService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
@PostMapping("/register")
public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
    authenticationService.register(request);
    return ResponseEntity.ok("User Registered Successfully");
    }
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
    return ResponseEntity.ok(authenticationService.login(request));
}
}
