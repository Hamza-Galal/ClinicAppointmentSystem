package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.DoctorScheduleRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.DoctorScheduleResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.InvalidScheduleException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.ScheduleConflictException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor 
@Service 
public class DoctorScheduleService {
    private final DoctorScheduleRepository scheduleRepo;
    private final DoctorRepository doctorRepo;

 public DoctorScheduleResponse createAvailability(Long doctorId , DoctorScheduleRequest request){
    Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor not found"));
    if (!request.getStartTime().isBefore(request.getEndTime())) {
        throw new InvalidScheduleException("Start Time must be before End Time");
    }
    boolean conflict = scheduleRepo.existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(doctorId,
         request.getDayOfWeek(),
         request.getStartTime(),
          request.getEndTime());
        if (conflict) {
            throw new ScheduleConflictException("Schedule Conflict");
        }
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        DoctorSchedule saved = scheduleRepo.save(schedule);
        return convertToResponse(saved);

 }
 public List<DoctorScheduleResponse> getDoctorAvailability(Long doctorId){
    if (!doctorRepo.existsById(doctorId)) {
        throw new DoctorNotFoundException("Doctor not found");
    }
    return scheduleRepo.findByDoctorId(doctorId).stream().map(this::convertToResponse).toList(); 
}

 private DoctorScheduleResponse convertToResponse(DoctorSchedule schedule){
    return  new DoctorScheduleResponse(
    schedule.getId(),schedule.getDoctor().getId(),
    schedule.getDayOfWeek(),schedule.getStartTime(),
    schedule.getEndTime());
 }
}
