package com.ubt.andi.businessrecommendation.services;


import com.ubt.andi.businessrecommendation.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesWithUsers();
    Role findRoleById(Long id);
    void updateRole(Role role);

}
