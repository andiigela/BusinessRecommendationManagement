package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BusinessRepository extends JpaRepository<Business,Long> {
    @Query("SELECT DISTINCT b FROM Business b JOIN FETCH b.images WHERE b.id = :id")
    Business findByIdWithImages(@Param("id") Long id);
}
