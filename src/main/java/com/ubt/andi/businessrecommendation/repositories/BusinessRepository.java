package com.ubt.andi.businessrecommendation.repositories;

import com.ubt.andi.businessrecommendation.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface BusinessRepository extends JpaRepository<Business,Long> {
    @Query("SELECT DISTINCT b FROM Business b JOIN FETCH b.images WHERE b.id = :id")
    Business findByIdWithImages(@Param("id") Long id);
    //@Query("SELECT DISTINCT b FROM Business b WHERE LOWER(b.name) LIKE CONCAT('%', LOWER(:name), '%')")
    //List<Business> findByNameWithImages(@Param("name") String name);
    List<Business> findByNameContainingIgnoreCase(String name);
    @Query("SELECT DISTINCT b from Business b JOIN FETCH b.applicationUser a where a.username=:username")
    List<Business> findBusinessesByApplicationUser(String username);
}
