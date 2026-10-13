ALTER TABLE doctor ADD CONSTRAINT chk_doctor_experience_nonnegative CHECK (years_of_experience >= 0);
ALTER TABLE doctor ADD CONSTRAINT chk_doctor_consultation_fee_nonnegative CHECK (consultation_fee >= 0);
ALTER TABLE doctor_schedule ADD CONSTRAINT chk_doctor_schedule_time_order CHECK (start_time < end_time);
