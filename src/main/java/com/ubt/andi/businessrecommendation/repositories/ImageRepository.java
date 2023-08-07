package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
