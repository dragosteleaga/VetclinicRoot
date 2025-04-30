package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.AnimalResponse;
import com.example.vetclinic.dto.OwnerResponse;
import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.model.Animal;
import com.example.vetclinic.service.OwnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@Tag(name = "Owners", description = "Endpoints for CRUD owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    // Get all owners
    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        List<OwnerResponse> owners = ownerService.getAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    // Get owner by ID
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long id) {
        OwnerResponse owner = ownerService.getOwnerById(id);
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    // Update an existing owner
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(
            @PathVariable Long id,
            @RequestBody RegisterRequest registerRequest) {
        OwnerResponse updatedOwner = ownerService.updateOwner(id, registerRequest);
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }

    // Delete an owner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //Add Animal to owner
    @PostMapping("/add-animal")
    public ResponseEntity<Void> addAnimal(@RequestBody AnimalResponse animalDTO) {
        ownerService.addAnimalToOwner(animalDTO.getOwnerId(), animalDTO.toAnimal());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
