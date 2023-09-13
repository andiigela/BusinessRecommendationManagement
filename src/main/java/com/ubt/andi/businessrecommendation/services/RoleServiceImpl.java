package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Role;
import com.ubt.andi.businessrecommendation.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    public List<Role> getRolesWithUsers(){
        return roleRepository.findAll();
    }
    @Override
    public Role findRoleById(Long id) {
        if(id == 0 || id == null) return null;
        return roleRepository.findById(id).get();
    }

    @Override
    public void updateRole(Role role) {
        if(role == null) return;
        roleRepository.save(role);
    }
}
