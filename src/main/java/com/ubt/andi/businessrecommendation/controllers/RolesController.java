package com.ubt.andi.businessrecommendation.controllers;

import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Role;
import com.ubt.andi.businessrecommendation.services.ApplicationUserService;
import com.ubt.andi.businessrecommendation.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RolesController {
    private RoleService roleService;
    private ApplicationUserService applicationUserService;
    @Autowired
    public RolesController(RoleService roleService,ApplicationUserService applicationUserService){
        this.roleService=roleService;
        this.applicationUserService=applicationUserService;
    }
    @GetMapping("/roles")
    public String getUserRoles(Model model){
        List<ApplicationUser> usersWithRoles = applicationUserService.getUsersWithRoles();
        Role userRole = roleService.findRoleById(1L);
        Role adminRole = roleService.findRoleById(2L);
        model.addAttribute("userRole",userRole);
        model.addAttribute("adminRole",adminRole);
        model.addAttribute("usersWithRoles",usersWithRoles);
        return "roles-list";
    }
    @PostMapping("/roles/remove/{userid}/{roleid}")
    public String removeRole(@PathVariable("userid") Long userId, @PathVariable("roleid") Long roleId) {
        ApplicationUser user = applicationUserService.findById(userId);
        Role role = roleService.findRoleById(roleId);
        Role adminRole = roleService.findRoleById(2L);
        Role userRole = roleService.findRoleById(1L);
        if (user != null && role != null) {
            if(user.getRoles().contains(role)){ // User ose Admin
                if( (role.getName().equals("User") && !user.getRoles().contains(adminRole))
                        || (role.getName().equals("Admin")
                                && !user.getRoles().contains(userRole))){
                    user.getRoles().remove(role);
                    role.getUsers().remove(user);
                    applicationUserService.updateUser(user);
                    roleService.updateRole(role);
                    applicationUserService.deleteUser(user);
                    return "redirect:/roles";
                }
                user.getRoles().remove(role);
                role.getUsers().remove(user);
                applicationUserService.updateUser(user);
                roleService.updateRole(role);
            }
        }
        return "redirect:/roles";
    }
    @PostMapping("/roles/addrole/{userid}/{roleid}")
    public String updateUserRoles(@PathVariable("userid") Long userId, @PathVariable("roleid") Long roleId){
        ApplicationUser existingUser = applicationUserService.findById(userId);
        Role role = roleService.findRoleById(roleId);
        if(!existingUser.getRoles().contains(role)){
            existingUser.getRoles().add(role);
            role.getUsers().add(existingUser);
        }
        applicationUserService.updateUser(existingUser);
        roleService.updateRole(role);
        return "redirect:/roles";
    }
}
