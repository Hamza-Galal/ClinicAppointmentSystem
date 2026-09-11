package com.ClinicSystem.ClinicAppointmentSystem.Controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.AppointmentCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AppointmentService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/appointments")
public class AppointmentController {
    private  final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }
@PostMapping
public ResponseEntity<AppointmentResponse> createAppointment(@Valid  @RequestBody AppointmentCreateRequest request){
    AppointmentResponse response = service.createAppointment(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
@GetMapping("/{id}")
public  ResponseEntity<AppointmentResponse> getAppointmentsById(@PathVariable Long id){
    return ResponseEntity.ok(service.getAppointmentById(id));
}
}
