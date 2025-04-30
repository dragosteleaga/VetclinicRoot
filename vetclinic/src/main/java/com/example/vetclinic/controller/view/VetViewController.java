package com.example.vetclinic.controller.view;

import com.example.vetclinic.dto.AppointmentResponse;
import com.example.vetclinic.dto.VeterinarianResponse;
import com.example.vetclinic.model.Appointment;
import com.example.vetclinic.service.AnimalService;
import com.example.vetclinic.service.AppointmentService;
import com.example.vetclinic.service.VeterinarianService;
import com.example.vetclinic.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/vet")
public class VetViewController {
    @Autowired
    private  AppointmentService appointmentService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VeterinarianService vetService;

    @Autowired
    private JwtUtil jwtUtil; // You'll need to create this utility class

    @GetMapping("/dashboard")
    public String getVetDashboard(
            @CookieValue(name = "jwtToken2", required = false) String cookieToken,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Long vetId = jwtUtil.getUserIdFromToken(cookieToken);
        VeterinarianResponse vet = vetService.getVeterinarianById(vetId);
        int pageSize = 3; // Number of items per page
        Page<Appointment> appointmentPage = appointmentService.getAppointmentsForVet(
                vetId,
                page - 1, pageSize, sort, direction
        );

        model.addAttribute("appointments", appointmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointmentPage.getTotalPages());
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDirection", direction);
        model.addAttribute("vet",vet);
        return "vet/dashboard";
}

    @GetMapping("/add-receipt/{appointmentId}")
    public String addReceipt(@CookieValue(name = "jwtToken2", required = false) String cookieToken,
                             Model model,
                             @PathVariable Long appointmentId){
        Long vetId = jwtUtil.getUserIdFromToken(cookieToken);
        VeterinarianResponse vet = vetService.getVeterinarianById(vetId);
        AppointmentResponse appointmentResponse=appointmentService.getAppointmentById(appointmentId);
        model.addAttribute("appointmentResponse",appointmentResponse);
        model.addAttribute("vet",vet);
        return "vet/add-receipt";
    }

}

