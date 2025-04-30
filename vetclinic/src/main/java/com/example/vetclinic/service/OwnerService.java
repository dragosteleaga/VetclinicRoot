package com.example.vetclinic.service;

import com.example.vetclinic.dto.OwnerResponse;
import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.mapper.OwnerMapper;
import com.example.vetclinic.model.Animal;
import com.example.vetclinic.model.Owner;
import com.example.vetclinic.repository.AnimalRepository;
import com.example.vetclinic.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private AnimalRepository animalRepository;

    // Get all owners
    public List<OwnerResponse> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream().map(ownerMapper::toResponse).collect(Collectors.toList());
    }

    // Get an owner by their ID
    public OwnerResponse getOwnerById(Long id) {
        Optional<Owner> ownerOpt = ownerRepository.findById(id);
        if (ownerOpt.isPresent()) {
            return ownerMapper.toResponse(ownerOpt.get());
        } else {
            throw new RuntimeException("Owner not found with id: " + id);
        }
    }

    // Update an owner
    public OwnerResponse updateOwner(Long id, RegisterRequest registerRequest) {
        Optional<Owner> existingOwnerOpt = ownerRepository.findById(id);
        if (existingOwnerOpt.isPresent()) {
            Owner existingOwner = existingOwnerOpt.get();
            existingOwner.setEmail(registerRequest.getEmail());
            existingOwner.setPassword(registerRequest.getPassword());
            existingOwner.setFirstName(registerRequest.getFirstName());
            existingOwner.setLastName(registerRequest.getLastName());
            existingOwner.setRole(registerRequest.getRole());

            Owner updatedOwner = ownerRepository.save(existingOwner);
            return ownerMapper.toResponse(updatedOwner);
        } else {
            throw new RuntimeException("Owner not found with id: " + id);
        }
    }

    // Delete an owner by their ID
    public void deleteOwner(Long id) {
        Optional<Owner> ownerOpt = ownerRepository.findById(id);
        if (ownerOpt.isPresent()) {
            ownerRepository.delete(ownerOpt.get());
        } else {
            throw new RuntimeException("Owner not found with id: " + id);
        }
    }
    public Owner findByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Owner not found"));
    }
    public void addAnimalToOwner(Long ownerId, Animal animal) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        // Set the owner for the animal and add it to the owner's list
        animal.setOwner(owner);
        owner.getAnimals().add(animal);

        // Save the updated owner
        ownerRepository.save(owner);
    }

}
