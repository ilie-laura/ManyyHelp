package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.ServiceDto;
import java.util.List;

public interface ServiceService {
    List<ServiceDto> findAllServices();
    List<ServiceDto> searchServices(String keyword);
    ServiceDto findServiceById(int id);
}