package com.example.vetclinic.controller.view;

import com.example.vetclinic.dto.OwnerResponse;
import com.example.vetclinic.model.Animal;
import com.example.vetclinic.model.Owner;
import com.example.vetclinic.service.OwnerService;
import com.example.vetclinic.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/owner")
public class OwnerViewController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private JwtUtil jwtUtil; // You'll need to create this utility class

    @GetMapping("/dashboard")
    public String dashboard(
            @CookieValue(name = "jwtToken2", required = false) String cookieToken,
            HttpServletRequest request,
            Model model) {

        // First try to get token from session
        String token = (String) request.getSession().getAttribute("jwtToken2");
        if (token == null) {
            token = cookieToken;
        }

        // Debug log
        System.out.println("Dashboard access - Token from session: " + (token != null ? "present" : "absent"));

        try {
            // Validate token
            boolean isValid = jwtUtil.validateToken(token);

            if (!isValid) {
                System.out.println("Token validation failed");
                return "redirect:/auth/login?error=invalid_token";
            }
            System.out.println("!!!"+isValid);
            // Extract user ID and load owner data
            Long userId = jwtUtil.getUserIdFromToken(token);
            System.out.println("Token validated for user ID: " + userId);

            // Get owner information - adjust this based on your service method
            OwnerResponse owner = ownerService.getOwnerById(userId);
            model.addAttribute("owner", owner);
            model.addAttribute("authenticated", true);

            return "owner/dashboard";
        } catch (Exception e) {
            // Token validation failed
            System.out.println("Token processing error: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/auth/login?error=token_error";
        }
    }
    @GetMapping("/animals/new")
    public String showAddAnimalForm(@CookieValue(name = "jwtToken2", required = false) String cookieToken,Model model,HttpServletRequest request) {
        String token = cookieToken;
        // Extract user ID and load owner data
        Long userId = jwtUtil.getUserIdFromToken(token);
        // Get owner information - adjust this based on your service method
        OwnerResponse owner = ownerService.getOwnerById(userId);
        model.addAttribute("owner", owner);
        model.addAttribute("animal", new Animal());

        return "owner/add-animal";
    }
    @GetMapping("/appointment/new")
    public String addAppointment(@CookieValue(name = "jwtToken2", required = false) String cookieToken,Model model,HttpServletRequest request) {
        System.out.println("token"+cookieToken);
        // Extract user ID and load owner data
        Long userId = jwtUtil.getUserIdFromToken(cookieToken);
        // Get owner information - adjust this based on your service method
        OwnerResponse owner = ownerService.getOwnerById(userId);
        model.addAttribute("owner", owner);
        return "owner/add-appointment";
    }
//    @PostMapping("/animals/save")
//    public String saveAnimal(@CookieValue(name = "jwtToken2", required = false) String cookieToken,@ModelAttribute Animal animal) {
//        Long ownerId = jwtUtil.getUserIdFromToken(cookieToken);
//        System.out.println("!!!"+cookieToken);
//        System.out.println("owner"+ownerId);
////        ownerService.addAnimalToOwner(ownerId, animal);
//        return "redirect:/owner/dashboard";
//    }
}

