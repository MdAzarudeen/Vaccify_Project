package com.Vaccify_Project.Vaccify_Project.Repositories;

import com.Vaccify_Project.Vaccify_Project.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer>
{
   Patient getByAadharNumber(int aadharNumber);

   Patient getByEmailId(String  emailId);
}
