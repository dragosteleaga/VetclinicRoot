package com.example.vetclinic.service;

import com.example.vetclinic.model.Animal;
import com.example.vetclinic.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    AnimalRepository animalRepository;
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }
    public Optional<Animal> getById(Long id){
        return animalRepository.findById(id);
    }
}
