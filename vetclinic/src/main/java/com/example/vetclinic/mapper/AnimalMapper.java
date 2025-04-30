package com.example.vetclinic.mapper;

import com.example.vetclinic.dto.AnimalResponse;
import com.example.vetclinic.model.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    public AnimalResponse toResponse(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setName(animal.getName());
        response.setSpecies(animal.getSpecies());
        response.setBreed(animal.getBreed());
        return response;
    }
}
