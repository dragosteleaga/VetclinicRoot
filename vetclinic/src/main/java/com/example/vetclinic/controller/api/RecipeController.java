package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.RecipeRequest;
import com.example.vetclinic.dto.RecipeResponse;
import com.example.vetclinic.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes", description = "Endpoints for managing recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeRequest request) {
        return new ResponseEntity<>(recipeService.createRecipe(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}

