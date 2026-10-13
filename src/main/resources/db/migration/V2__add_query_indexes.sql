CREATE INDEX idx_appointment_doctor_date_time ON appointment (doctor_id, appointment_date, appointment_time);
CREATE INDEX idx_appointment_patient_date_time ON appointment (patient_id, appointment_date, appointment_time);
CREATE INDEX idx_appointment_status_date ON appointment (status, appointment_date);
CREATE INDEX idx_waiting_list_slot_fifo ON waiting_list_entries (doctor_id, appointment_date, appointment_time, joined_at);
CREATE INDEX idx_waiting_list_patient ON waiting_list_entries (patient_id);
