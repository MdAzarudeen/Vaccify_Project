package com.Vaccify_Project.Vaccify_Project.Controllers;

import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @PostMapping("add-appointment")
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment){
        try{
            String response = appointmentService.addAppointment(appointment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllAppointmentsOfDoctor/{id}")
    public ResponseEntity<List<String>> getAllAppointmentsOfDoctor(@PathVariable Integer id){
        List<String> list = appointmentService.getAllAppointmentsOfDoctor(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByPatientId/{id}")
    public Appointment getAppointment(@PathVariable Integer id)
    {
        return appointmentService.getAppointmentByPatientId(id);
    }

    @GetMapping("/getAppointmentByCenterId/{id}")
    public List<String> getAppointmentByCenter(@PathVariable Integer id)
    {
        return appointmentService.getAppointmentByVaccinationCenterId(id);
    }

    @PostMapping("vaccination/{id}")
    public String processVaccination(@PathVariable Integer id)
    {
        return appointmentService.processVaccination(id);
    }
}
