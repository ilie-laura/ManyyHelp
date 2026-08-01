package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {


    @Query("SELECT s FROM Service s WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(s.numeServiciu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.Locatie) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Service> searchServices(@Param("keyword") String keyword);
}