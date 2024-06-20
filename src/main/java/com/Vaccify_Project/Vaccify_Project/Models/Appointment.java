package com.Vaccify_Project.Vaccify_Project.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    private int vaccinationCenterId;
    private String centerName;
    private int  doctorId;
    private String doctorName;
    private int patientId;
    private  String patientName;
    private LocalDateTime appointmentDateTime;
    private String isVaccinated;

    @ManyToOne
    private Doctor doctor;


}
