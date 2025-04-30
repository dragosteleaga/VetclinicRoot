package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("VETERINARIAN")
@Getter
@Setter
public class Veterinarian extends User {
    private String specialization;  // Specific attribute for veterinarians
    @OneToMany(mappedBy = "veterinarian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

}