package com.Vaccify_Project.Vaccify_Project.Controllers;

import com.Vaccify_Project.Vaccify_Project.Exceptions.PatientNotFoundException;
import com.Vaccify_Project.Vaccify_Project.Models.Patient;
import com.Vaccify_Project.Vaccify_Project.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;
    @PostMapping("/add-patient")
    public ResponseEntity<String> addUser(@RequestBody Patient patient){
        try
        {
            String response = patientService.addPatient(patient);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (PatientNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get-patient-by-email/{email}")
    public ResponseEntity<Patient> getUserByEmail(@PathVariable String email){
        try
        {
            Patient obj = patientService.getPatientByEmail(email);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }catch(PatientNotFoundException e)
        {
            e.getMessage();
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
