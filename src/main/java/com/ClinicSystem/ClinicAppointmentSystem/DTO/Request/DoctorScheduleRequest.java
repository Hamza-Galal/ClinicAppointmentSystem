package com.ClinicSystem.ClinicAppointmentSystem.DTO.Request;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
public class DoctorScheduleRequest {
    @NotNull(message = "Day of the week required")
    private  DayOfWeek dayOfWeek;
    @NotNull(message = "Start Time is required")
    private LocalTime startTime;
    @NotNull(message = "End Time is required")
    private LocalTime endTime;
}
