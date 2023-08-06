package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Business;

import java.util.List;

public interface BusinessService {
    List<Business> getBusinesses();
    void createBusiness(Business business);
    Business getBusinessById(Long id);
    void updateBusiness(Business business);
    void deleteBusiness(Long id);
}
