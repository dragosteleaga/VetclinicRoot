package com.example.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponse {
    private Long id;
    private LocalDateTime date;
    private String details;
    private Double price;
    private VeterinarianResponse veterinarian;
    private OwnerResponse owner;
    private AnimalResponse animal;
    private RecipeResponse recipe;
}

