package com.Vaccify_Project.Vaccify_Project.Services;

import com.Vaccify_Project.Vaccify_Project.Exceptions.PatientNotFoundException;
import com.Vaccify_Project.Vaccify_Project.Models.Patient;
import com.Vaccify_Project.Vaccify_Project.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;
    public String addPatient(Patient patient) throws PatientNotFoundException {
        Patient existingPatient = patientRepository.getByAadharNumber(patient.getAadharNumber());
        if (existingPatient!=null) {
            throw new PatientNotFoundException("Patient "+existingPatient.getName()+" already registered using this Aadhar Number. \nKindly modify your Aadhar number");
        }

        Patient existingPatient2 = patientRepository.getByEmailId(patient.getEmailId());
        if (existingPatient2!=null) {
            throw new PatientNotFoundException("Patient "+existingPatient2.getName()+" already registered using this Mail address.\nKindly modify your Mail address");
        }
        patientRepository.save(patient);
        return "Patient "+patient.getName()+" has been added successfully";
    }

    public Patient getPatientByEmail(String email) throws PatientNotFoundException {
        Patient existingUser = patientRepository.getByEmailId(email);
        if (existingUser==null) {
            throw new PatientNotFoundException("Patient does not exists with this mail address "+email);
        }
        return existingUser;
    }
}
