package com.Vaccify_Project.Vaccify_Project.Services;

import com.Vaccify_Project.Vaccify_Project.DTOs.ConnectDocCenter;
import com.Vaccify_Project.Vaccify_Project.Exceptions.CenterNotFoundException;
import com.Vaccify_Project.Vaccify_Project.Exceptions.DoctorAlreadyExistsException;
import com.Vaccify_Project.Vaccify_Project.Exceptions.DoctorNotFoundException;
import com.Vaccify_Project.Vaccify_Project.Exceptions.EmailIdInvalidException;
import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Models.Doctor;
import com.Vaccify_Project.Vaccify_Project.Models.VaccinationCenter;
import com.Vaccify_Project.Vaccify_Project.Repositories.DoctorRepository;
import com.Vaccify_Project.Vaccify_Project.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public String addDoctor(Doctor doctor) throws EmailIdInvalidException, DoctorAlreadyExistsException
    {
        if(Objects.isNull(doctor.getEmailId()) || doctor.getEmailId().equals(""))throw new EmailIdInvalidException("Invalid Email Id");
        Doctor doc = doctorRepository.findByEmailId(doctor.getEmailId());
        if(doc!=null)
            throw new DoctorAlreadyExistsException("Doctor already exists in the vaccine portal");
        doctorRepository.save(doctor);
        return "Doctor "+doctor.getName()+" has been added successfully";
    }

    public String addDocToCenter(ConnectDocCenter connectDocCenter) throws DoctorNotFoundException,CenterNotFoundException
    {
        Optional<Doctor> doctorOptional = doctorRepository.findById(connectDocCenter.getDocId());
        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(connectDocCenter.getCenterId());
        if(doctorOptional.isEmpty())throw new DoctorNotFoundException("Doctor not present with ID "+connectDocCenter.getDocId());
        if(vaccinationCenterOptional.isEmpty())throw new CenterNotFoundException("Vaccination Center is not present with ID "+connectDocCenter.getCenterId());
        Doctor doc = doctorOptional.get();
        VaccinationCenter center = vaccinationCenterOptional.get();
        doc.setVaccinationCenter(center);
        center.getDoctorList().add(doc);
        vaccinationCenterRepository.save(center);
        doctorRepository.save(doc);
        return "Doctor "+doc.getName() +" has been successfully assigned to the "+center.getCenterName();
    }


    public List<String> getDoctorsAgedAbove40() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<String> doctorList = new ArrayList<>();
        for(Doctor doc : doctors){
            if(doc.getAge()>40 )
                doctorList.add(doc.getName());
        }
        return doctorList;
    }

    public List<String> getDoctorsList(Integer id) {
        List<Doctor> doctorList = vaccinationCenterRepository.findById(id).get().getDoctorList();
        List<String> list =  new ArrayList<>();
        for(Doctor doctor: doctorList)
        {
            list.add(doctor.getName());
        }
        return  list;
    }
}
