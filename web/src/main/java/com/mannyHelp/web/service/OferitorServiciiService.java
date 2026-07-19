package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.models.OferitorServicii;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OferitorServiciiService {
    List<OferitorServiciiDto> findAllOferitori();


}
