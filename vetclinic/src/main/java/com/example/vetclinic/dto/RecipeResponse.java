package com.example.vetclinic.dto;

import com.example.vetclinic.model.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeResponse {
    private Long id;
    private String description;
    private List<String> drugNames;
    private AppointmentResponse appointment;  // Using AppointmentResponse
}