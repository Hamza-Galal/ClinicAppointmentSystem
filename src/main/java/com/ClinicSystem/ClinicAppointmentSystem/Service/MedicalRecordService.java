package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.util.List;

import com.ClinicSystem.ClinicAppointmentSystem.Exception.*;
import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.MedicalRecordCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.MedicalRecordResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Appointment;
import com.ClinicSystem.ClinicAppointmentSystem.Model.MedicalRecord;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Enums.AppointmentStatus;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.AppointmentRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.MedicalRecordRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordResponse createMedicalRecord(
            Long appointmentId,
            MedicalRecordCreateRequest request) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException("Appointment NOT Found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new InvalidMedicalRecordException(
                    "Medical record can only be created for a completed appointment");
        }

        if (medicalRecordRepository.existsByAppointmentId(appointmentId)) {
            throw new DuplicateMedicalRecordException(
                    "Medical record already exists for this appointment");
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAppointment(appointment);
        medicalRecord.setOutcome(request.getOutcome());

        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);

        return convertToResponse(saved);
    }

    public List<MedicalRecordResponse> getPatientMedicalRecords(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient Not Found"));

        return medicalRecordRepository.findByAppointmentPatientId(patientId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public MedicalRecordResponse getMedicalRecordById(Long id) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() ->
                        new MedicalRecordNotFoundException("Medical Record NOT Found"));

        return convertToResponse(medicalRecord);
    }

    private MedicalRecordResponse convertToResponse(
            MedicalRecord medicalRecord) {

        Appointment appointment = medicalRecord.getAppointment();

        return new MedicalRecordResponse(
                medicalRecord.getId(),
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                medicalRecord.getOutcome());
    }
}