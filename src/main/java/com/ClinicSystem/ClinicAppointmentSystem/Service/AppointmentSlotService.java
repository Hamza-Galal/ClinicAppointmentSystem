package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AvailableSlotResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;
@Service 
public class AppointmentSlotService {
    private DoctorRepository doctorRepository;
    private DoctorScheduleRepository doctorScheduleRepository;
    private AppointmentRepository appointmentRepository;

    public AppointmentSlotService(DoctorRepository doctorRepository, DoctorScheduleRepository doctorScheduleRepository,
            AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.appointmentRepository = appointmentRepository;
    }
    public List<AvailableSlotResponse> getAvailableSlots(Long doctorId , LocalDate date){
        doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor Not Found"));
        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDoctorId(doctorId);
        List<DoctorSchedule> daySchedules = schedules.stream().filter(schedule -> schedule.getDayOfWeek()
        .equals(date.getDayOfWeek())).toList();
        List<LocalTime> allSlots = new ArrayList<>();
        for(DoctorSchedule schedule : daySchedules){
            LocalTime currentTime = schedule.getStartTime();
            while (currentTime.plusMinutes(30).compareTo(schedule.getEndTime())<=0) {
                allSlots.add(currentTime);
                currentTime = currentTime.plusMinutes(30);
            }
        }
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);

        List<LocalTime> availableSlots = new ArrayList<>();
        for (LocalTime slot : allSlots) {
            boolean booked = false;
            for(Appointment appointment : appointments){
                if (appointment.getAppointmentTime().equals(slot) 
                    && appointment.getStatus() != AppointmentStatus.CANCELLED ) {
                    booked = true;
                    break;
                }
            }
            if (!booked) {
                availableSlots.add(slot);
            }
        }
        
        return availableSlots.stream().map(AvailableSlotResponse::new).toList();
    }
    
}
