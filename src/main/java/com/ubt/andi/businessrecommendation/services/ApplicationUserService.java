package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;

public interface ApplicationUserService {
    void saveUser(ApplicationUser user);
    void updateUser(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    ApplicationUser findByUsername(String username);
    ApplicationUser findByConfirmationToken(String token);
    ApplicationUser findById(Long id);
}
