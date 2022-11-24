package com.example.renderfarm.service;

import com.example.renderfarm.entity.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findAppUserByUsername(String username);

    void signUpUser(AppUser appUser);
}
