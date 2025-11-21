/**
 * @author: Bek
 * Date: 01/06/2025
 * Time: 12:03
 * Project Name: patient-management
 */

package com.pm.patientservice.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {


    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException ex) {

        Map<String, String> errros = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errros.put(error.getField(), error.getDefaultMessage()));


        return ResponseEntity.badRequest().body(errros);
    }

    @ExceptionHandler(EmailAreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyException(EmailAreadyExistsException ex) {

        log.error("Email address already exist {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email already exists!");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> patientNotFoundException(PatientNotFoundException ex) {

        log.error("Patient not found with this id {}",ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found with this id ");
        return ResponseEntity.badRequest().body(errors);

    }
}