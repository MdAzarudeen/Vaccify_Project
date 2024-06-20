package com.Vaccify_Project.Vaccify_Project.Models;

import com.Vaccify_Project.Vaccify_Project.Enums.Dosage;
import com.Vaccify_Project.Vaccify_Project.Enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="nameOfPatient")
    private String name;
    private String mobile;

    private int age;

    @Column(unique = true)
    private int aadharNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String emailId;
    @Enumerated(EnumType.STRING)
    private Dosage preferredDose;


}
