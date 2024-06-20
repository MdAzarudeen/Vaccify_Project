package com.Vaccify_Project.Vaccify_Project.Controllers;

import com.Vaccify_Project.Vaccify_Project.DTOs.ConnectDocCenter;
import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Models.Doctor;
import com.Vaccify_Project.Vaccify_Project.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController
{
    @Autowired
    DoctorService doctorService;
    @PostMapping("/add-doctor")
    public String addDoctor(@RequestBody Doctor doctor){
        try{
            String response = doctorService.addDoctor(doctor);
            return response;
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    @PutMapping("/connect-doctor-with-center")
    public ResponseEntity<String> addDocToCenter(@RequestBody ConnectDocCenter connectDocCenter){
        try{
            String response = doctorService.addDocToCenter(connectDocCenter);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-doctors-aged-above-40")
    public ResponseEntity<List<String>> getDoctorsAgedAbove40(){

        List<String> doctors = doctorService.getDoctorsAgedAbove40();
        return new ResponseEntity<>(doctors, HttpStatus.OK);

    }

    @GetMapping("/getDoctorsList/{id}")
    public List<String> getDoctorsList(@PathVariable Integer id)
    {
        return doctorService.getDoctorsList(id);
    }
}
