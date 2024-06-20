package com.Vaccify_Project.Vaccify_Project.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="VaccinationCenter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccinationCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int centerId;
    private String centerName;
    private int covishieldCount;
    private int covaxinCount;
    private int sputnikCount;
    private String address;

    @OneToMany(mappedBy = "vaccinationCenter",cascade = CascadeType.ALL)
    private List<Doctor> doctorList = new ArrayList<>();
}
