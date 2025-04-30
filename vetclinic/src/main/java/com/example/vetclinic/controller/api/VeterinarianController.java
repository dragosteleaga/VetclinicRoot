package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.dto.VeterinarianResponse;
import com.example.vetclinic.service.VeterinarianService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarians")
@Tag(name = "Veterinarians", description = "Endpoints for CRUD veterinarians")
public class VeterinarianController {

    @Autowired
    private VeterinarianService veterinarianService;

    // Endpoint to get all veterinarians
    @GetMapping
    public ResponseEntity<List<VeterinarianResponse>> getAllVeterinarians() {
        List<VeterinarianResponse> veterinarians = veterinarianService.getAllVeterinarians();
        return new ResponseEntity<>(veterinarians, HttpStatus.OK);
    }

    // Endpoint to get a veterinarian by their ID
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> getVeterinarianById(@PathVariable Long id) {
        VeterinarianResponse veterinarian = veterinarianService.getVeterinarianById(id);
        return new ResponseEntity<>(veterinarian, HttpStatus.OK);
    }


    // Endpoint to update an existing veterinarian
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> updateVeterinarian(
            @PathVariable Long id,
            @RequestBody RegisterRequest registerRequest) {
        VeterinarianResponse updatedVeterinarian = veterinarianService.updateVeterinarian(id, registerRequest);
        return new ResponseEntity<>(updatedVeterinarian, HttpStatus.OK);
    }

    // Endpoint to delete a veterinarian by their ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian(@PathVariable Long id) {
        veterinarianService.deleteVeterinarian(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
