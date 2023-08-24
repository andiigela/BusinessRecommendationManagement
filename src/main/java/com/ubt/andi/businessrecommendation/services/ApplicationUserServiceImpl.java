package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Role;
import com.ubt.andi.businessrecommendation.repositories.ApplicationUserRepository;
import com.ubt.andi.businessrecommendation.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private ApplicationUserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository userRepository,RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public void saveUser(ApplicationUser user) {
        if(user == null) return;
        Role role = roleRepository.findByName("User");
        if(role == null) return;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public ApplicationUser findByEmail(String email) {
        if(email.trim().isEmpty() || email == null) return null;
        return userRepository.findByEmail(email);
    }
    @Override
    public ApplicationUser findByUsername(String username) {
        if(username.trim().isEmpty() || username == null) return null;
        return userRepository.findByUsername(username);
    }
}
