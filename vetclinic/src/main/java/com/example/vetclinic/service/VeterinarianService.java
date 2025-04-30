package com.example.vetclinic.service;

import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.dto.VeterinarianResponse;
import com.example.vetclinic.mapper.VeterinarianMapper;
import com.example.vetclinic.model.Appointment;
import com.example.vetclinic.model.Veterinarian;
import com.example.vetclinic.repository.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeterinarianService {
    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private VeterinarianMapper veterinarianMapper;

    // Method to get all veterinarians
    public List<VeterinarianResponse> getAllVeterinarians() {
        List<Veterinarian> veterinarians = veterinarianRepository.findAll();
        return veterinarians.stream()
                .map(veterinarianMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Method to get a veterinarian by their ID
    public VeterinarianResponse getVeterinarianById(Long id) {
        Optional<Veterinarian> veterinarianOpt = veterinarianRepository.findById(id);
        if (veterinarianOpt.isPresent()) {
            return veterinarianMapper.toResponse(veterinarianOpt.get());
        } else {
            // Handle veterinarian not found, maybe throw an exception
            throw new RuntimeException("Veterinarian not found with id: " + id);
        }
    }

    // Method to update an existing veterinarian
    public VeterinarianResponse updateVeterinarian(Long id, RegisterRequest registerRequest) {
        Optional<Veterinarian> existingVeterinarianOpt = veterinarianRepository.findById(id);
        if (existingVeterinarianOpt.isPresent()) {
            Veterinarian existingVeterinarian = existingVeterinarianOpt.get();

            // Update fields from the RegisterRequest
            existingVeterinarian.setEmail(registerRequest.getEmail());
            existingVeterinarian.setPassword(registerRequest.getPassword());
            existingVeterinarian.setFirstName(registerRequest.getFirstName());
            existingVeterinarian.setLastName(registerRequest.getLastName());
            existingVeterinarian.setRole(registerRequest.getRole());
            existingVeterinarian.setSpecialization(registerRequest.getSpecialization());

            // Save the updated veterinarian to the repository
            Veterinarian updatedVeterinarian = veterinarianRepository.save(existingVeterinarian);

            // Return the updated veterinarian as a VeterinarianResponse
            return veterinarianMapper.toResponse(updatedVeterinarian);
        } else {
            // Handle veterinarian not found, maybe throw an exception
            throw new RuntimeException("Veterinarian not found with id: " + id);
        }
    }

    // Method to delete a veterinarian by their ID
    public void deleteVeterinarian(Long id) {
        Optional<Veterinarian> veterinarianOpt = veterinarianRepository.findById(id);
        if (veterinarianOpt.isPresent()) {
            veterinarianRepository.delete(veterinarianOpt.get());
        } else {
            // Handle veterinarian not found, maybe throw an exception
            throw new RuntimeException("Veterinarian not found with id: " + id);
        }
    }
    public List<Appointment> getVetAppointments(Long id) {
        Optional<Veterinarian> veterinarianOpt = veterinarianRepository.findById(id);
        return veterinarianOpt.map(Veterinarian::getAppointments).orElse(Collections.emptyList());

    }
}
