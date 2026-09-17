package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter  
public class AvailableSlotResponse {
    private LocalTime time;


}
