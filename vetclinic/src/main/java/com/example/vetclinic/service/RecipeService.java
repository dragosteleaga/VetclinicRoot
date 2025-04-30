package com.example.vetclinic.service;

import com.example.vetclinic.dto.RecipeRequest;
import com.example.vetclinic.dto.RecipeResponse;
import com.example.vetclinic.mapper.RecipeMapper;
import com.example.vetclinic.model.Appointment;
import com.example.vetclinic.model.Drug;
import com.example.vetclinic.model.Recipe;
import com.example.vetclinic.repository.AppointmentRepository;
import com.example.vetclinic.repository.DrugRepository;
import com.example.vetclinic.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<RecipeResponse> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RecipeResponse getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return recipeMapper.toResponse(recipe);
    }

    public RecipeResponse createRecipe(RecipeRequest request) {
        // Convert the RecipeRequest to Recipe entity
        Recipe recipe = recipeMapper.toEntity(request);

        // Fetch the Appointment by ID
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(request.getAppointmentId());

        // If the Appointment is not found, handle the error (e.g., throw an exception)
        if (appointmentOptional.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found with ID: " + request.getAppointmentId());
        }

        Appointment appointment = appointmentOptional.get();

        // Set the Recipe on the Appointment (establish the relationship)
        appointment.setRecipe(recipe);


        // Fetch the Drugs by their IDs (ensure the drugRepository has a proper method to find by IDs)
        List<Drug> drugs = drugRepository.findAllById(request.getDrugIds());

        // If Drugs are not found, handle the error (e.g., throw an exception)
        if (drugs.isEmpty()) {
            throw new IllegalArgumentException("Drugs not found for the provided drug IDs.");
        }

        // Set the drugs on the recipe
        recipe.setDrugs(drugs);
        // Save the Recipe (and the associated Appointment if it's a cascading relationship)
        recipe = recipeRepository.save(recipe);

        // Optionally, update the Appointment if necessary
        appointmentRepository.save(appointment);

        // Return the RecipeResponse
        return recipeMapper.toResponse(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
