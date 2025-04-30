package com.example.vetclinic.controller;

import com.example.vetclinic.dto.OwnerResponse;
import com.example.vetclinic.model.Owner;
import com.example.vetclinic.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class OwnerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testGetAllOwners() throws Exception {
        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

    }

    @Test
    void testGetOwnerById() throws Exception {
        mockMvc.perform(get("/api/owners/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.animals", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testUpdateOwner() throws Exception {
        String updatedOwner = """
            {
                "email": "owner1@example.com",
                "password": "newpassword",
                "firstName": "Johnny",
                "lastName": "Doeman"
            }
            """;
        mockMvc.perform(put("/api/owners/5")
                        .contentType("application/json")
                        .content(updatedOwner))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Johnny"));
    }

}
