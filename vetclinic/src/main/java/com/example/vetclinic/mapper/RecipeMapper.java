package com.example.vetclinic.mapper;

import com.example.vetclinic.dto.AppointmentResponse;
import com.example.vetclinic.dto.DrugResponse;
import com.example.vetclinic.dto.RecipeRequest;
import com.example.vetclinic.dto.RecipeResponse;
import com.example.vetclinic.model.Appointment;
import com.example.vetclinic.model.Drug;
import com.example.vetclinic.model.Recipe;
import com.example.vetclinic.repository.AppointmentRepository;
import com.example.vetclinic.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Recipe toEntity(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setDescription(request.getDescription());

        // Map drugs based on the drug IDs provided in the request
        List<Drug> drugs = drugRepository.findAllById(request.getDrugIds());
        recipe.setDrugs(drugs);

        // Map the appointment by ID (assuming appointmentId is provided)
        if (request.getAppointmentId() != null) {
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(request.getAppointmentId());
            appointmentOpt.ifPresent(recipe::setAppointment);
        }

        return recipe;
    }

    public RecipeResponse toResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setDescription(recipe.getDescription());

        // Map drug names
        response.setDrugNames(
                recipe.getDrugs().stream().map(Drug::getName).toList()
        );

        // Map the appointment to the response (if it exists)
        if (recipe.getAppointment() != null) {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            appointmentResponse.setId(recipe.getAppointment().getId());
            appointmentResponse.setDate(recipe.getAppointment().getDate());
            appointmentResponse.setDetails(recipe.getAppointment().getDetails());
            response.setAppointment(appointmentResponse);
        }

        return response;
    }
}

