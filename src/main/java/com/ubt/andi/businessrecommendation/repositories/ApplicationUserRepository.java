package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findByEmail(String email);
    ApplicationUser findByUsername(String username);
}
