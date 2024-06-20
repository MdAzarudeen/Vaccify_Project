package com.Vaccify_Project.Vaccify_Project.Services;

import com.Vaccify_Project.Vaccify_Project.Enums.Dosage;
import com.Vaccify_Project.Vaccify_Project.Exceptions.*;
import com.Vaccify_Project.Vaccify_Project.Models.Appointment;
import com.Vaccify_Project.Vaccify_Project.Models.Doctor;
import com.Vaccify_Project.Vaccify_Project.Models.Patient;
import com.Vaccify_Project.Vaccify_Project.Models.VaccinationCenter;
import com.Vaccify_Project.Vaccify_Project.Repositories.AppointmentRepository;
import com.Vaccify_Project.Vaccify_Project.Repositories.DoctorRepository;
import com.Vaccify_Project.Vaccify_Project.Repositories.PatientRepository;
import com.Vaccify_Project.Vaccify_Project.Repositories.VaccinationCenterRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.awt.SystemColor.text;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired VaccinationCenterService vaccinationCenterService;
    @Autowired PatientService patientService;
    @Autowired DoctorService doctorService;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public String addAppointment(Appointment appointment) throws DoctorNotFoundException,PatientNotFoundException,CenterNotFoundException,AppointmentAlreadyDoneException{
        Optional<Doctor> doctorOptional =  doctorRepository.findById(appointment.getDoctorId());
        if(doctorOptional.isEmpty())
        {
            throw new DoctorNotFoundException("Doctor with ID " +appointment.getDoctorId()+" is not present in the system");
        }
        else
        {
            VaccinationCenter vaccinationCenter = doctorOptional.get().getVaccinationCenter();
            if(vaccinationCenter.getCenterId()!=appointment.getVaccinationCenterId())
            {
                throw new DoctorNotFoundException("Doctor with ID " +appointment.getDoctorId()+" is not assocaited with " +
                        "Vaccination center with ID "+appointment.getVaccinationCenterId());
            }
        }

        Optional<Patient> patientOptional = patientRepository.findById(appointment.getPatientId());
        if(patientOptional.isEmpty())
            throw new PatientNotFoundException("Patient with ID "+appointment.getPatientId()+ " is not present in the system");

        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(appointment.getVaccinationCenterId());
        if(vaccinationCenterOptional.isEmpty())
        {
            throw new CenterNotFoundException("Vaccination Center with ID "+appointment.getVaccinationCenterId()+" is not present in the system");
        }

        Doctor doctor = doctorOptional.get();
        Patient patient = patientOptional.get();
        VaccinationCenter vaccinationCenter = vaccinationCenterOptional.get();

        Appointment existingAppointment = appointmentRepository.getByPatientId(patient.getId());
        if(existingAppointment!=null)
            throw new AppointmentAlreadyDoneException(patient.getName()+" has already made an appointment at "+vaccinationCenter.getCenterName()+" with Doctor "+doctor.getName());


        Dosage dosage = patient.getPreferredDose();

        if((dosage.equals(Dosage.Sputnik) && vaccinationCenter.getSputnikCount()<1)||
                (dosage.equals(Dosage.Covaxin) && vaccinationCenter.getCovaxinCount()<1)||
                (dosage.equals(Dosage.Covishield) && vaccinationCenter.getCovishieldCount()<1)
        )
        {
            throw new DosageNotAvailableException(dosage.toString()+" dose is not available currently at "+vaccinationCenter.getCenterName());
        }

        if(dosage.equals(Dosage.Sputnik) && vaccinationCenter.getSputnikCount()>0)
            vaccinationCenter.setSputnikCount(vaccinationCenter.getSputnikCount()-1);
        else if(dosage.equals(Dosage.Covishield) && vaccinationCenter.getCovishieldCount()>0)
            vaccinationCenter.setCovishieldCount(vaccinationCenter.getCovishieldCount()-1);
        else if(dosage.equals(Dosage.Covaxin) && vaccinationCenter.getCovaxinCount()>0)
            vaccinationCenter.setCovaxinCount(vaccinationCenter.getCovaxinCount()-1);

        LocalDateTime currentDateTime = LocalDateTime.now();
        appointment.setAppointmentDateTime(currentDateTime.plusHours(24));
        appointment.setIsVaccinated("Not Vaccinated");
        appointment.setCenterName(vaccinationCenter.getCenterName());
        appointment.setDoctorName(doctor.getName());
        appointment.setPatientName(patient.getName());
        appointmentRepository.save(appointment);
        doctor.getAppointmentList().add(appointment);
        doctorRepository.save(doctor);



        String mail = patient.getEmailId();
        String text = "Hey! Your appointment has been booked.\n\n" +
                "Kindly find the below details of your appointment.\n\n" +
                "Vaccination Center: "+vaccinationCenter.getCenterName()+
                "\n\n Doctor :"+doctor.getName()+"\n\n Appointment Date & Time :"
                +currentDateTime.plusHours(24);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("vaccinationjavaproject@gmail.com");
        simpleMailMessage.setTo(patient.getEmailId());
        simpleMailMessage.setSubject("Vaccination Appointment update");
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);


        return "Appointment booked for "+patient.getName()+" and mail has been sent successfully";
    }

    public List<String> getAllAppointmentsOfDoctor(Integer id) {
        List<Appointment> appointmentList = appointmentRepository.getByDoctorId(id);
        List<String> list =  new ArrayList<>();
        for(Appointment appointment: appointmentList)
        {
            list.add(appointment.getPatientName());
        }
        return  list;
    }

    public Appointment getAppointmentByPatientId(Integer id)
    {
        return appointmentRepository.getByPatientId(id);
    }

    public List<String> getAppointmentByVaccinationCenterId(Integer id) {
        List<Appointment> appointmentList = appointmentRepository.getByVaccinationCenterId(id);
        List<String> list =  new ArrayList<>();
        for(Appointment appointment: appointmentList)
        {
            list.add(appointment.getPatientName()+" has made an appointment with Doctor "+appointment.getDoctorName()+
                    " for "+appointment.getAppointmentDateTime());
        }
        return  list;
    }

    public String processVaccination(Integer id)
    {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(patientOptional.isEmpty())
            throw new PatientNotFoundException("Patient with ID "+id+ " is not present in the system");

        Appointment appointment = appointmentRepository.getByPatientId(id);
        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(appointment.getVaccinationCenterId());
        if(vaccinationCenterOptional.isEmpty())
        {
            throw new CenterNotFoundException("Vaccination Center with ID "+appointment.getVaccinationCenterId()+" is not present in the system");
        }
        Optional<Doctor> doctorOptional =  doctorRepository.findById(appointment.getDoctorId());
        if(doctorOptional.isEmpty())
        {
            throw new DoctorNotFoundException("Doctor with ID " +appointment.getDoctorId()+" is not present in the system");
        }
        Doctor doctor = doctorOptional.get();
        VaccinationCenter vaccinationCenter = vaccinationCenterOptional.get();
        Patient patient = patientOptional.get();

        if(appointment.getIsVaccinated().equals("Vaccinated"))
        {
            throw new AlreadyVaccinatedException("Patient has been already vaccinated");
        }
        appointment.setIsVaccinated("Vaccinated");


        String htmlContent = "<html>" +
                "<body>" +
                "<p style='font-size:30px; font-weight:bold;'>Congratulations! You are vaccinated.</p>" +
                "<br>" +
                "<p style='font-size:14px;'>This is to certify that you have been vaccinated successfully at " +
                "<span style='font-size:14px; font-weight:bold;'>" + vaccinationCenter.getCenterName() + "</span>" +
                " by Doctor " +
                "<span style='font-size:14px; font-weight:bold;'>" + doctor.getName() + "</span>" +
                " on " +
                "<span style='font-size:14px;'>" + appointment.getAppointmentDateTime() + "</span>" +
                ".</p>" +
                "</body>" +
                "</html>";

        try {
            sendHtmlEmail(patient.getEmailId(), "Vaccination Successful update", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }

        return "Vaccination successful for  "+patient.getName()+" and mail has been sent successfully";
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("vaccinationjavaproject@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
