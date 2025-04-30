package com.example.vetclinic.mapper;
import com.example.vetclinic.dto.DrugRequest;
import com.example.vetclinic.dto.DrugResponse;
import com.example.vetclinic.model.Drug;
import org.springframework.stereotype.Component;

@Component
public class DrugMapper {

    public Drug toEntity(DrugRequest request) {
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setDescription(request.getDescription());
        drug.setPrice(request.getPrice());
        return drug;
    }

    public DrugResponse toResponse(Drug drug) {
        DrugResponse response = new DrugResponse();
        response.setId(drug.getId());
        response.setName(drug.getName());
        response.setDescription(drug.getDescription());
        response.setPrice(drug.getPrice());
        return response;
    }
}
