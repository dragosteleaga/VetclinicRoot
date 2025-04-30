package com.example.vetclinic.repository;

import com.example.vetclinic.model.Owner;
import com.example.vetclinic.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
