package com.mannyHelp.web.repository;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.models.OferitorServicii;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OferitorServiciiRepository extends JpaRepository<OferitorServicii,Long> {


}
