package com.ubt.andi.businessrecommendation.services;

import com.ubt.andi.businessrecommendation.models.Rating;

public interface RatingService {
    Rating findRating(Long businessId, Long userId);
}
