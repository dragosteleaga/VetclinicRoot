package com.example.vetclinic.exception;

public class SpecializationRequiredException extends RuntimeException {
    public SpecializationRequiredException(String message){
        super(message);
    }
}