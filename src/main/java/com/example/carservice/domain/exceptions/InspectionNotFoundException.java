package com.example.carservice.domain.exceptions;

public class InspectionNotFoundException extends RuntimeException{
    public InspectionNotFoundException(String message){
        super(message);
    }
}
