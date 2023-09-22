package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;

import java.util.List;

public interface ApplicationUserService {
    void saveUser(ApplicationUser user);
    void updateUser(ApplicationUser user);
    ApplicationUser findByEmail(String email);
    ApplicationUser findByUsername(String username);
    ApplicationUser findByConfirmationToken(String token);
    ApplicationUser findById(Long id);
    List<ApplicationUser> getUsersWithRoles();
    void deleteUser(ApplicationUser user);
}
