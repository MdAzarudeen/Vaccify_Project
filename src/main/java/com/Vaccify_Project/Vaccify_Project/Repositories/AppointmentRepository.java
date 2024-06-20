package com.Vaccify_Project.Vaccify_Project.Repositories;

import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer>
{
    Appointment getByPatientId(Integer pid);

    List<Appointment> getByDoctorId(Integer id);

    List<Appointment> getByVaccinationCenterId(Integer id);
}
