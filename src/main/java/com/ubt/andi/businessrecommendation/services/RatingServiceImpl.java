package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Rating;
import com.ubt.andi.businessrecommendation.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    public RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository=ratingRepository;
    }

    @Override
    public Rating findRating(Long businessId, Long userId) {
        if(businessId == 0 || businessId == null) return null;
        if(userId == 0 || userId == null) return null;
        return ratingRepository.findByBusinessIdAndAndUserId(businessId,userId);
    }
}
