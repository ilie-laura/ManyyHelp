package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Recenzie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzieRepository extends JpaRepository<Recenzie, Integer> {


    List<Recenzie> findByProviderProviderid(Long providerId);


    List<Recenzie> findByProviderUserUserid(Long userId);
}