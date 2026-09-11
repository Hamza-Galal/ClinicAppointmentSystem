package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.AppointmentCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.AppointmentResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.AppointmentConflictException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.AppointmentNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.OutsideWorkingHoursException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.PatientNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor 
public class AppointmentService {
    private  final AppointmentRepository appointmentRepo;
    private  final DoctorScheduleRepository doctorScheduleRepository;
    private  final PatientRepository  patientRepository;
    private  final DoctorRepository   doctorRepository;

    public  AppointmentResponse getAppointmentById(Long id){
        Appointment appointment = appointmentRepo.findById(id)
        .orElseThrow(()-> new AppointmentNotFoundException("Appointment NOT Found"));
        return convertToResponse(appointment);
    }

    public AppointmentResponse createAppointment(AppointmentCreateRequest request){
        Patient patient = patientRepository.findById(request.getPatientId())
        .orElseThrow(()-> new PatientNotFoundException("Patient Not Found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId()).
        orElseThrow(()-> new DoctorNotFoundException("Doctor Not Found"));

        LocalDateTime appointmenDateTime = LocalDateTime.of(request.getAppointmentDate(), request.getAppointmentTime());
        if (!appointmenDateTime.isAfter(LocalDateTime.now())){
            throw new AppointmentConflictException("Appointment cannot be in the past");
        }

        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDoctorId(doctor.getId());
        boolean withinSchedule = schedules.stream().
        anyMatch(schedule -> schedule.getDayOfWeek().equals(request.getAppointmentDate().getDayOfWeek())
         && !request.getAppointmentTime().isBefore(schedule.getStartTime())
         && !request.getAppointmentTime().isBefore(schedule.getEndTime()));

         if (!withinSchedule) {
            throw new OutsideWorkingHoursException("Appointment time is outside the doctor's working hours");
         }

         boolean doctorConflict = appointmentRepo.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(doctor.getId(), 
         request.getAppointmentDate(), 
         request.getAppointmentTime(), 
         AppointmentStatus.CANCELLED);

         if (doctorConflict) {
            throw new AppointmentConflictException("Doctor Already Has An Appointment at this time");
         }

           boolean PatientConflict = appointmentRepo.existsBypatientIdAndAppointmentDateAndAppointmentTimeAndStatusNot(patient.getId(), 
         request.getAppointmentDate(), 
         request.getAppointmentTime(), 
         AppointmentStatus.CANCELLED);

         if (PatientConflict) {
            throw new AppointmentConflictException("Patient Already Has An Appointment at this time");
         }
         Appointment appointment = new Appointment();
         appointment.setPatient(patient);
         appointment.setDoctor(doctor);
         appointment.setAppointmentDate(request.getAppointmentDate());
         appointment.setAppointmentTime(request.getAppointmentTime());
         appointment.setReasonForVisit(request.getReasonForVisit());
         appointment.setStatus(AppointmentStatus.SCHEDULED);
         Appointment saved = appointmentRepo.save(appointment);
         return convertToResponse(saved);
    }   

    private  AppointmentResponse convertToResponse(Appointment appointment){
        return  new AppointmentResponse(appointment.getId(),
    appointment.getPatient().getId(),
    appointment.getDoctor().getId(),
    appointment.getAppointmentDate(),
    appointment.getAppointmentTime(),
    appointment.getReasonForVisit(),
    appointment.getStatus());
    }
}
