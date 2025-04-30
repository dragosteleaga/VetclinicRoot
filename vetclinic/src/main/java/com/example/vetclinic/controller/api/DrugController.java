package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.DrugRequest;
import com.example.vetclinic.dto.DrugResponse;
import com.example.vetclinic.service.DrugService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@Tag(name = "Drugs", description = "Endpoints for managing drugs")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @GetMapping
    public ResponseEntity<List<DrugResponse>> getAllDrugs() {
        return new ResponseEntity<>(drugService.getAllDrugs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrugResponse> getDrugById(@PathVariable Long id) {
        return new ResponseEntity<>(drugService.getDrugById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DrugResponse> createDrug(@RequestBody DrugRequest request) {
        return new ResponseEntity<>(drugService.createDrug(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrugResponse> updateDrug(@PathVariable Long id, @RequestBody DrugRequest request) {
        return new ResponseEntity<>(drugService.updateDrug(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
