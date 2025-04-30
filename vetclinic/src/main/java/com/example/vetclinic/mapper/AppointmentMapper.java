package com.example.vetclinic.mapper;

import com.example.vetclinic.dto.AppointmentResponse;
import com.example.vetclinic.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    @Autowired
    private VeterinarianMapper veterinarianMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private AnimalMapper animalMapper;

    @Autowired
    private RecipeMapper recipeMapper;
    public AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDate(appointment.getDate());
        response.setDetails(appointment.getDetails());
        response.setPrice(appointment.getPrice());
        // Set the Veterinarian, Owner, and Animal
        response.setVeterinarian(veterinarianMapper.toResponse(appointment.getVeterinarian()));
        response.setOwner(ownerMapper.toResponse(appointment.getOwner()));
        response.setAnimal(animalMapper.toResponse(appointment.getAnimal()));

        // If the appointment has a recipe, set it
        if (appointment.getRecipe() != null) {
            response.setRecipe(recipeMapper.toResponse(appointment.getRecipe()));
        }

        return response;
    }
}
