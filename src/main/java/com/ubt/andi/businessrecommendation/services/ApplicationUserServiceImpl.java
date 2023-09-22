package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.models.Role;
import com.ubt.andi.businessrecommendation.repositories.ApplicationUserRepository;
import com.ubt.andi.businessrecommendation.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.getRoles().add(role);
        userRepository.save(user);
    }
    @Override
    public void updateUser(ApplicationUser user){
        if(user == null) return;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
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
    @Override
    public ApplicationUser findById(Long id) {
        if(id == 0 || id == null) return null;
        return userRepository.findApplicationUserById(id);
    }
    @Override
    public ApplicationUser findByConfirmationToken(String token) {
        if(token.trim().isEmpty() || token == null) return null;
        return userRepository.findByConfirmationToken(token);
    }
    @Override
    public List<ApplicationUser> getUsersWithRoles() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(ApplicationUser user) {
        if(user == null) return;
        userRepository.delete(user);
    }
}
