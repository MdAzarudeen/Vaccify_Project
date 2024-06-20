package com.Vaccify_Project.Vaccify_Project.Repositories;

import com.Vaccify_Project.Vaccify_Project.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer>
{
    Doctor findByEmailId(String emailId);

}
