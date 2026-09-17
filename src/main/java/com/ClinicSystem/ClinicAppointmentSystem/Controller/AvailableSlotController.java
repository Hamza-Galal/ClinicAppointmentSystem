package com.ClinicSystem.ClinicAppointmentSystem.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AvailableSlotResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Service.AppointmentSlotService;

@RestController 
@RequestMapping("/api/doctors")
public class AvailableSlotController {
    private final AppointmentSlotService service;

    public AvailableSlotController(AppointmentSlotService service) {
        this.service = service;
    }
    
    @GetMapping("/{doctorId}/available-slots")
    public  ResponseEntity<List<AvailableSlotResponse>> getAvailableSlots(@PathVariable long doctorId , @RequestParam LocalDate date){
        return ResponseEntity.ok(service.getAvailableSlots(doctorId, date));
    }
}
