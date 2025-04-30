package com.example.vetclinic.service;

import com.example.vetclinic.dto.DrugRequest;
import com.example.vetclinic.dto.DrugResponse;
import com.example.vetclinic.mapper.DrugMapper;
import com.example.vetclinic.model.Drug;
import com.example.vetclinic.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugMapper drugMapper;

    public List<DrugResponse> getAllDrugs() {
        return drugRepository.findAll().stream()
                .map(drugMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DrugResponse getDrugById(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));
        return drugMapper.toResponse(drug);
    }

    public DrugResponse createDrug(DrugRequest request) {
        Drug savedDrug = drugRepository.save(drugMapper.toEntity(request));
        return drugMapper.toResponse(savedDrug);
    }

    public DrugResponse updateDrug(Long id, DrugRequest request) {
        Drug existing = drugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        return drugMapper.toResponse(drugRepository.save(existing));
    }

    public void deleteDrug(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));
        drugRepository.delete(drug);
    }
}
