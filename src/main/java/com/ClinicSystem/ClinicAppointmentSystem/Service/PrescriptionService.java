package com.ClinicSystem.ClinicAppointmentSystem.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ClinicSystem.ClinicAppointmentSystem.DTO.Request.PrescriptionCreateRequest;
import com.ClinicSystem.ClinicAppointmentSystem.DTO.Response.PrescriptionResponse;
import com.ClinicSystem.ClinicAppointmentSystem.Exception.MedicalRecordNotFoundException;
import com.ClinicSystem.ClinicAppointmentSystem.Model.MedicalRecord;
import com.ClinicSystem.ClinicAppointmentSystem.Model.Prescription;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.MedicalRecordRepository;
import com.ClinicSystem.ClinicAppointmentSystem.Repository.PrescriptionRepository;
@Service 
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    
    public PrescriptionService(PrescriptionRepository prescriptionRepository,
            MedicalRecordRepository medicalRecordRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public PrescriptionResponse createPrescription(Long recordId , PrescriptionCreateRequest request){
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId).
        orElseThrow(()-> new MedicalRecordNotFoundException("Medical Record Not Found"));
        Prescription p = new Prescription();
        p.setMedicationName(request.getMedicationName());
        p.setDosage(request.getDosage());
        p.setFrequency(request.getFrequency());
        p.setTreatmentDuration(request.getTreatmentDuration());
        p.setAdditionalInstructions(request.getAdditionalInstructions());
        p.setMedicalRecord(medicalRecord);
        Prescription saved = prescriptionRepository.save(p);
        return convertToResponse(saved);
    }
    public List<PrescriptionResponse> getPrescriptionsByRecord(Long id){
        if (!medicalRecordRepository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical Record Not Found");
        }
        List<Prescription> prescriptions = prescriptionRepository.findByMedicalRecordId(id);
        List<PrescriptionResponse> responses = new ArrayList<>();
            for (Prescription prescription : prescriptions) {
                responses.add(convertToResponse(prescription));
            }
            return  responses;
    }

    public List<PrescriptionResponse> getPrescriptionsByPatient(Long id){
        if (!medicalRecordRepository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical Record for this patient is Not Found");
        }
        List<Prescription> prescriptions = prescriptionRepository.findByMedicalRecordAppointmentPatientId(id);
        List<PrescriptionResponse> responses = new ArrayList<>();
            for (Prescription prescription : prescriptions) {
                responses.add(convertToResponse(prescription));
            }
            return  responses;
    }

    private PrescriptionResponse convertToResponse(Prescription prescription){
        return new PrescriptionResponse(prescription.getId(),
        prescription.getMedicalRecord().getId(),
        prescription.getMedicalRecord().getAppointment().getPatient().getId(),
    prescription.getMedicationName(),
    prescription.getDosage(),
    prescription.getFrequency(),
    prescription.getTreatmentDuration(),
    prescription.getAdditionalInstructions());
    }
}
