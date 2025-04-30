package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.AppointmentRequest;
import com.example.vetclinic.dto.AppointmentResponse;
import com.example.vetclinic.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "Endpoints for CRUD appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    // Endpoint to create a new appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentService.createAppointment(request);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // Endpoint to get an appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentResponse appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // Endpoint to update an appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(id, request);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    // Endpoint to delete an appointment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
