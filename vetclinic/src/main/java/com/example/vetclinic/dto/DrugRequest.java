package com.example.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugRequest {
    private String name;
    private String description;
    private double price;
}

