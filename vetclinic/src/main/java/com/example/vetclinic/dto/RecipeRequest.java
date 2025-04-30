package com.example.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeRequest {
    private String description;
    private List<Long> drugIds;
    private Long appointmentId;   // ID of the appointment associated with the recipe
}