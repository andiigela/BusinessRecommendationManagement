package com.ubt.andi.businessrecommendation.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {
    public static String getSessionUser(){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if(!(authUser instanceof AnonymousAuthenticationToken)){
            String currentUsername = authUser.getName();
            return currentUsername;
        }
        return null;
    }
}
