package com.example.vetclinic.repository;

import com.example.vetclinic.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {
}
