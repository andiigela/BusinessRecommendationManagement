package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.controllers.SecurityContextUtil;
import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.repositories.BusinessRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {
    private BusinessRepository businessRepository;
    private ApplicationUserService userService;
    public BusinessServiceImpl(BusinessRepository businessRepository,
                               ApplicationUserService userService){
        this.businessRepository=businessRepository;
        this.userService=userService;
    }

    @Override
    public Business getBusinessById(Long id) {
        if(id == 0 || id == null) return null;
        return businessRepository.findByIdWithImages(id);
    }

    @Override
    public void updateBusiness(Business business) {
        if(business == null) return;
        String email = SecurityContextUtil.getSessionUser();
        ApplicationUser authUser = userService.findByUsername(email);
        business.setApplicationUser(authUser);
        businessRepository.save(business);
    }
    @Override
    public void updateBusinessWithoutUser(Business business){
        if(business == null) return;
        businessRepository.save(business);
    }
    /*
    @Override
    public List<Business> findBusinessesByName(String name) {
        if(name.trim().isEmpty() || name == null) return null;
        return businessRepository.findByNameContainingIgnoreCase(name);
    }*/
    @Override
    public void deleteBusiness(Long id) {
        if(id == 0 || id == null) return;
        businessRepository.deleteById(id);
    }

    @Override
    public void createBusiness(Business business) {
        if(business == null) return;
        String username = SecurityContextUtil.getSessionUser();
        ApplicationUser authUser = userService.findByUsername(username);
        business.setApplicationUser(authUser);
        businessRepository.save(business);
    }

    @Override
    public List<Business> getBusinesses() {
        String username = SecurityContextUtil.getSessionUser();
        return businessRepository.findBusinessesByApplicationUser(username);
    }

    @Override
    public List<Business> findBusinessesByNotApplicationUser(String username,String businessName) {
        String usernamee = SecurityContextUtil.getSessionUser();
        return businessRepository.findBusinessesByNotApplicationUser(usernamee,businessName);
    }

    @Override
    public Business findBusiness(Long id) {
        if(id == 0 || id == null) return null;
        return businessRepository.findById(id).get();
    }
}
