package com.example.renderfarm.service.impl;

import com.example.renderfarm.dto.request.RegistrationRequestDto;
import com.example.renderfarm.entity.AppUser;
import com.example.renderfarm.service.AppUserService;
import com.example.renderfarm.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("registrationServiceImpl")
public class RegistrationServiceImpl implements RegistrationService {
    private final AppUserService appUserService;

    @Override
    public void register(RegistrationRequestDto dto) {
        appUserService.signUpUser(new AppUser(
                dto.getUsername(), dto.getPassword()
        ));
    }
}
