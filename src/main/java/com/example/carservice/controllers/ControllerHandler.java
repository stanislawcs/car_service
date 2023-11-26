package com.example.carservice.controllers;

import com.example.carservice.domain.ExceptionResponse;
import com.example.carservice.domain.exceptions.CarNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(CarNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleValidationsException(MethodArgumentNotValidException e) {

        ExceptionResponse response = new ExceptionResponse();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            response.setMessage(message + " ");
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleInspectionNotFoundException(InstantiationException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
