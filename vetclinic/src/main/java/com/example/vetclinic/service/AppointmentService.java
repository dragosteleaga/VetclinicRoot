package com.example.vetclinic.service;

import com.example.vetclinic.dto.AppointmentRequest;
import com.example.vetclinic.dto.AppointmentResponse;
import com.example.vetclinic.mapper.AppointmentMapper;
import com.example.vetclinic.model.Appointment;
import com.example.vetclinic.model.Animal;
import com.example.vetclinic.model.Owner;
import com.example.vetclinic.model.Veterinarian;
import com.example.vetclinic.repository.AppointmentRepository;
import com.example.vetclinic.repository.AnimalRepository;
import com.example.vetclinic.repository.OwnerRepository;
import com.example.vetclinic.repository.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    // Create a new appointment
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // Retrieve the necessary entities
        Optional<Veterinarian> veterinarianOpt = veterinarianRepository.findById(request.getVeterinarianId());
        Optional<Animal> animalOpt = animalRepository.findById(request.getAnimalId());
        Optional<Owner> ownerOpt = ownerRepository.findById(request.getOwnerId()); // assuming ownerId is available in request

        if (veterinarianOpt.isEmpty() || animalOpt.isEmpty() || ownerOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid veterinarian, animal, or owner ID.");
        }

        // Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setDate(request.getDate());
        appointment.setDetails(request.getDetails());
        appointment.setPrice(request.getPrice());
        appointment.setVeterinarian(veterinarianOpt.get());
        appointment.setAnimal(animalOpt.get());
        appointment.setOwner(ownerOpt.get());

        // If a recipeId is provided, fetch the recipe and set it
        if (request.getRecipeId() != null) {
            // Logic for setting the recipe (could be fetched from a RecipeRepository)
        }

        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(appointment);
    }

    // Get appointment by ID
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));
        return appointmentMapper.toResponse(appointment);
    }

    // Update an existing appointment (if needed)
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));

        // Update fields based on request
        appointment.setDate(request.getDate());
        appointment.setDetails(request.getDetails());
        appointment.setPrice(request.getPrice());

        // Save and return updated appointment
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(appointment);
    }

    // Delete an appointment by ID
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
    }
    /**
     * Get paginated and sorted appointments for a specific veterinarian
     * @param vetId The veterinarian's ID
     * @param page The page number (0-based)
     * @param size The page size
     * @param sortField The field to sort by
     * @param direction The sort direction (asc/desc)
     * @return A page of appointments
     */
    public Page<Appointment> getAppointmentsForVet(Long vetId, int page, int size, String sortField, String direction) {
        logger.info("Fetching appointments for veterinarian ID: {} with page: {}, size: {}, sort: {}, direction: {}",
                vetId, page, size, sortField, direction);

        // Validate veterinarian exists
        if (!veterinarianRepository.existsById(vetId)) {
            logger.warn("Attempted to fetch appointments for non-existent veterinarian ID: {}", vetId);
            throw new IllegalArgumentException("Veterinarian not found with ID: " + vetId);
        }

        // Create sort object
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortField);

        // Create page request
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Fetch appointments
        Page<Appointment> appointments = appointmentRepository.findByVeterinarianId(vetId, pageRequest);

        logger.info("Found {} appointments for veterinarian ID: {}", appointments.getTotalElements(), vetId);
        return appointments;}
}
