package com.ClinicSystem.ClinicAppointmentSystem.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ClinicSystem.ClinicAppointmentSystem.Model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,Long> {
    List<DoctorSchedule> findByDoctorId(Long doctorId);
    boolean existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
        Long doctorId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
    );
}
