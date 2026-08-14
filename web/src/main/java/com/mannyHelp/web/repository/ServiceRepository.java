package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query("SELECT s FROM Service s WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            " LOWER(s.numeServiciu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(s.Locatie) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(s.categorie) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND " +
            "(:location IS NULL OR :location = '' OR " +
            " LOWER(s.Locatie) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<Service> searchServices(@Param("keyword") String keyword, @Param("location") String location);

    @Query("SELECT s FROM Service s WHERE s.provider.userid = :userId")
    List<Service> findByProviderUserid(@Param("userId") Long userId);
}