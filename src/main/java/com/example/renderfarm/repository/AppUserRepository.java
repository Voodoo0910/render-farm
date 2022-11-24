package com.example.renderfarm.repository;

import com.example.renderfarm.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByUsername(String username);

    Optional<AppUser> findFirstByUsername(String username);
}
