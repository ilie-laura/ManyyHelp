package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.ServiceDto;
import java.util.List;

public interface ServiceService {
    List<ServiceDto> findAllServices();
    List<ServiceDto> searchServices(String keyword,String location);
    ServiceDto findServiceById(int id);
    List<ServiceDto> findServicesByProviderUserId(Long userId);
    void saveService(ServiceDto serviceDto);

    List<ServiceDto> searchAndFilterServices(String keyword, String location, String category, String sortBy);
}