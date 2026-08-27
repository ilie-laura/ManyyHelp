package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Recenzie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzieRepository extends JpaRepository<Recenzie, Integer> {


    List<Recenzie> findByProviderProviderid(Long providerId);
    List<Recenzie> findByProviderUserUserid(Long userId);
    List<Recenzie> findByServiceServiceidOrderByCreatedAtDesc(int serviceId);
    @Query("SELECT AVG(r.rating) FROM Recenzie r WHERE r.service.serviceid = :serviceId")
    Double getAverageRatingByServiceId(@Param("serviceId") int serviceId);
    @Query(value = "SELECT * FROM recenzie WHERE userid = :userId ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Recenzie> findByUserUseridOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("limit") int limit);
    
    @Query(value = "SELECT * FROM recenzie ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Recenzie> findRecentPlatformReviews(@Param("limit") int limit);

}