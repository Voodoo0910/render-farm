package com.example.renderfarm.controller;

import com.example.renderfarm.dto.request.RegistrationRequestDto;
import com.example.renderfarm.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Qualifier("registrationServiceImpl")
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDto request) {
        registrationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
