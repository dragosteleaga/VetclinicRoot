package com.example.vetclinic.controller;

import com.example.vetclinic.model.User;
import com.example.vetclinic.model.Veterinarian;
import com.example.vetclinic.repository.UserRepository;
import com.example.vetclinic.repository.VeterinarianRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@Sql("/data.sql")
public class VeterinarianControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllVets() throws Exception {
        mockMvc.perform(get("/api/veterinarians"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
    @Test
    void testGetVeterinarianById() throws Exception {

        mockMvc.perform(get("/api/veterinarians/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.email").value("vet2@clinic.com")); // Adjust based on your test data
    }
    @Test
    void testUpdateVeterinarian() throws Exception {
        String updateJson = """
        {
            "firstName": "Updated",
            "lastName": "Vet",
            "specialization": "Surgery",
            "email":"vet2@clinic.com"
        }
    """;

        mockMvc.perform(put("/api/veterinarians/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.specialization").value("Surgery"));
    }
    @Test
    void testDeleteVeterinarian() throws Exception {
        mockMvc.perform(delete("/api/veterinarians/3"))
                .andExpect(status().isNoContent());
    }


}
