package com.ClinicSystem.ClinicAppointmentSystem.DTO.Response;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
public class DoctorScheduleResponse {
    private long id;
    private long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
