package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
