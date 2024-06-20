package com.Vaccify_Project.Vaccify_Project.Services;

import com.Vaccify_Project.Vaccify_Project.Exceptions.VaccinationAddressEmptyException;
import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Models.Doctor;
import com.Vaccify_Project.Vaccify_Project.Models.VaccinationCenter;
import com.Vaccify_Project.Vaccify_Project.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VaccinationCenterService {

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public String addVaccinationCenter(VaccinationCenter center) throws VaccinationAddressEmptyException {
        if(Objects.isNull(center.getAddress()))
            throw new VaccinationAddressEmptyException("Address not found ");

        vaccinationCenterRepository.save(center);
        return "New Vaccination Center created successfully";
    }

}
