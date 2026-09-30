package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.WaitingListRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.WaitingListResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DoctorNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.DuplicateWaitingListException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.OutsideWorkingHoursException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.PatientNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.WaitingListEntryNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.WaitingListSlotAvailableException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Doctor;
import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Patient;
import com.ClinicSystem.ClinicAppointmentSystem.Model.WaitingListEntry;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.DoctorScheduleRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.WaitingListEntryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WaitingListService {

    private final WaitingListEntryRepository waitingListRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    public WaitingListResponse joinWaitingList(Long doctorId, WaitingListRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found"));

        if (!request.getAppointmentDate().isAfter(java.time.LocalDate.now())) {
            throw new WaitingListSlotAvailableException("Waiting list date must be in the future");
        }

        boolean withinSchedule = doctorScheduleRepository.findByDoctorId(doctorId)
                .stream()
                .anyMatch(schedule ->
                        schedule.getDayOfWeek().equals(request.getAppointmentDate().getDayOfWeek())
                                && !request.getAppointmentTime().isBefore(schedule.getStartTime())
                                && request.getAppointmentTime().isBefore(schedule.getEndTime()));

        if (!withinSchedule) {
            throw new OutsideWorkingHoursException(
                    "Appointment time is outside the doctor's working hours");
        }

        boolean occupied = appointmentRepository
                .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                        doctorId,
                        request.getAppointmentDate(),
                        request.getAppointmentTime(),
                        AppointmentStatus.CANCELLED);

        if (!occupied) {
            throw new WaitingListSlotAvailableException(
                    "Appointment slot is available and does not require a waiting list");
        }

        boolean duplicate = waitingListRepository
                .existsByPatientIdAndDoctorIdAndAppointmentDateAndAppointmentTime(
                        patient.getId(),
                        doctorId,
                        request.getAppointmentDate(),
                        request.getAppointmentTime());

        if (duplicate) {
            throw new DuplicateWaitingListException(
                    "Patient is already on the waiting list for this appointment slot");
        }

        WaitingListEntry entry = new WaitingListEntry();
        entry.setPatient(patient);
        entry.setDoctor(doctor);
        entry.setAppointmentDate(request.getAppointmentDate());
        entry.setAppointmentTime(request.getAppointmentTime());
        entry.setJoinedAt(LocalDateTime.now());

        WaitingListEntry saved = waitingListRepository.save(entry);

        return toResponse(saved, false);
    }

    public List<WaitingListResponse> getWaitingList(
            Long doctorId,
            java.time.LocalDate appointmentDate,
            java.time.LocalTime appointmentTime) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        List<WaitingListEntry> entries =
                waitingListRepository.findByDoctorIdAndAppointmentDateAndAppointmentTimeOrderByJoinedAtAsc(
                        doctorId,
                        appointmentDate,
                        appointmentTime);

        return java.util.stream.IntStream.range(0, entries.size())
                .mapToObj(i -> toResponse(entries.get(i), i == 0))
                .toList();
    }

    public void leaveWaitingList(
            Long doctorId,
            Long patientId,
            java.time.LocalDate appointmentDate,
            java.time.LocalTime appointmentTime) {

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found"));

        WaitingListEntry entry = waitingListRepository
                .findByPatientIdAndDoctorIdAndAppointmentDateAndAppointmentTime(
                        patientId,
                        doctorId,
                        appointmentDate,
                        appointmentTime)
                .orElseThrow(() -> new WaitingListEntryNotFoundException(
                        "Patient is not on the waiting list for this appointment slot"));

        waitingListRepository.delete(entry);
    }

    public WaitingListResponse getFirstEligiblePatient(
            Long doctorId,
            java.time.LocalDate appointmentDate,
            java.time.LocalTime appointmentTime) {

        List<WaitingListEntry> entries =
                waitingListRepository.findByDoctorIdAndAppointmentDateAndAppointmentTimeOrderByJoinedAtAsc(
                        doctorId,
                        appointmentDate,
                        appointmentTime);

        if (entries.isEmpty()) {
            return null;
        }

        return toResponse(entries.get(0), true);
    }

    private WaitingListResponse toResponse(
            WaitingListEntry entry,
            boolean firstEligible) {

        return new WaitingListResponse(
                entry.getId(),
                entry.getPatient().getId(),
                entry.getDoctor().getId(),
                entry.getAppointmentDate(),
                entry.getAppointmentTime(),
                entry.getJoinedAt(),
                firstEligible);
    }
}
