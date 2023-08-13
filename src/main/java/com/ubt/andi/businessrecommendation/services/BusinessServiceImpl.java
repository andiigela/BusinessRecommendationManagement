package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.repositories.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BusinessServiceImpl implements BusinessService {
    private BusinessRepository businessRepository;
    public BusinessServiceImpl(BusinessRepository businessRepository){
        this.businessRepository=businessRepository;
    }

    @Override
    public Business getBusinessById(Long id) {
        if(id == 0 || id == null) return null;
        return businessRepository.findByIdWithImages(id);
    }

    @Override
    public void updateBusiness(Business business) {
        if(business == null) return;
        businessRepository.save(business);
    }

    @Override
    public List<Business> findBusinessesByName(String name) {
        if(name.trim().isEmpty() || name == null) return null;
        return businessRepository.findByNameContainingIgnoreCase(name);
    }
    @Override
    public void deleteBusiness(Long id) {
        if(id == 0 || id == null) return;
        businessRepository.deleteById(id);
    }

    @Override
    public void createBusiness(Business business) {
        if(business == null) return;
        businessRepository.save(business);
    }

    @Override
    public List<Business> getBusinesses() {
        return businessRepository.findAll();
    }
}
