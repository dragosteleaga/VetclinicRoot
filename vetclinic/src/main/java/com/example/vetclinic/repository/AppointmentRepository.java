package com.example.vetclinic.repository;

import com.example.vetclinic.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.veterinarian.id = :vetId")
    Page<Appointment> findByVeterinarianId(@Param("vetId") Long vetId, Pageable pageable);
}