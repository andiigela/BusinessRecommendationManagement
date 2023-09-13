package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findByEmail(String email);
    ApplicationUser findByUsername(String username);
    ApplicationUser findByConfirmationToken(String token);
    ApplicationUser findApplicationUserById(Long id);
}
