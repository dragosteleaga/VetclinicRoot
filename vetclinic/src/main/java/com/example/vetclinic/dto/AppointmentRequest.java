package com.example.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {
    private Long ownerId;
    private Long veterinarianId;
    private Long animalId;
    private LocalDateTime date;
    private String details;
    private Double price;
    private Long recipeId;
}

