package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.OferitorServiciiDto;

import java.util.List;

public interface OferitorServiciiService {
    List<OferitorServiciiDto> findAllOferitori();
    void saveOferitorServicii(OferitorServiciiDto dto);

    OferitorServiciiDto findByServiceId(int serviceId);
}