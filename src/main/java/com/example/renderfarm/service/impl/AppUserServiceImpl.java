package com.example.renderfarm.service.impl;

import com.example.renderfarm.entity.AppUser;
import com.example.renderfarm.entity.AppUserRole;
import com.example.renderfarm.exception.custom.UsernameAlreadyTakenException;
import com.example.renderfarm.repository.AppUserRepository;
import com.example.renderfarm.repository.AppUserRoleRepository;
import com.example.renderfarm.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.renderfarm.constant.Constants.USERNAME_ALREADY_TAKEN_MSG;
import static com.example.renderfarm.constant.Constants.USERNAME_NOT_FOUND_MSG;

@Service("appUserServiceImpl")
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final AppUserRoleRepository appUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository
                .findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MSG, username)));
    }

    @Override
    public Optional<AppUser> findAppUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }

    @Override
    @Transactional
    public void signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findAppUserByUsername(appUser.getUsername()).isPresent();

        if (userExists) {
            throw new UsernameAlreadyTakenException(String.format(USERNAME_ALREADY_TAKEN_MSG, appUser.getUsername()));
        }

        AppUserRole appUserRole = appUserRoleRepository.findByName("USER").orElseThrow();
        appUserRole.addAppUserToRole(appUser);
        appUserRoleRepository.save(appUserRole);

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.addRoleToUser(appUserRole);
        appUserRepository.save(appUser);
    }
}
