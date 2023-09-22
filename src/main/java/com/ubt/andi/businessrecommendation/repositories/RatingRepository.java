package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.Rating;
import com.ubt.andi.businessrecommendation.models.RatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RatingRepository extends JpaRepository<Rating, RatingPK> {
    Rating findByBusinessIdAndAndUserId(Long businessId, Long userId);
}
