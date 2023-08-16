package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Modifying
    @Transactional
    @Query("Delete from Image where photoUrl=:url")
    void deleteImageByUrl(@Param("url") String url);
}
