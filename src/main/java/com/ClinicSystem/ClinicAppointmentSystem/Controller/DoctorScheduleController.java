package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorScheduleRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorScheduleResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.DoctorScheduleService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/doctors/{doctorId}/availability")
public class DoctorScheduleController {
 private  final DoctorScheduleService service;

 public DoctorScheduleController(DoctorScheduleService service) {
    this.service = service;
 }
 
 @PostMapping 
 public ResponseEntity<DoctorScheduleResponse> createAvailability(@PathVariable Long doctorId ,
    @Valid @RequestBody DoctorScheduleRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createAvailability(doctorId, request));    
 }
 @GetMapping 
  public ResponseEntity<List<DoctorScheduleResponse>> getDoctorAvailability(@PathVariable Long doctorId){
    return ResponseEntity.ok(service.getDoctorAvailability(doctorId));
  }
 
}
