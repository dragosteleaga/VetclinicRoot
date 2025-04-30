package com.example.vetclinic.controller.view;

import com.example.vetclinic.dto.AnimalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.example.vetclinic.model.Animal;
import com.example.vetclinic.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping
    public String listAnimals(Model model) {
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "animals"; // Looks for animals.html in /templates
    }
    @GetMapping("/{id}")
    public String viewAnimalDetails(@PathVariable("id") Long id, Model model) {
        Optional<Animal> animal = animalService.getById(id); // Replace with your actual service method
        System.out.println(animal.get().getName());
        model.addAttribute("animal", animal.get());
        return "animal/details"; // Thymeleaf template at src/main/resources/templates/animal/details.html
    }
}

