package com.Vaccify_Project.Vaccify_Project.Controllers;

import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Models.VaccinationCenter;
import com.Vaccify_Project.Vaccify_Project.Services.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccinationCenter")
public class VaccinationCenterController {
    @Autowired
    VaccinationCenterService vaccinationCenterService;
    @PostMapping("/add-center")
    public String addCenter(@RequestBody VaccinationCenter center){
        return vaccinationCenterService.addVaccinationCenter(center);
    }


}
