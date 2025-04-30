package com.example.vetclinic.repository;

import com.example.vetclinic.model.Owner;
import com.example.vetclinic.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);
}
