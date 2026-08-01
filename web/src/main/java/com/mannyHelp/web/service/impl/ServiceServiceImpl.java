package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.models.Service;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.service.ServiceService;


import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<ServiceDto> findAllServices() {
        List<Service> services = serviceRepository.findAll();
        return services.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    @Override
    public List<ServiceDto> searchServices(String keyword, String location) {
        
        String cleanKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String cleanLocation = (location != null && !location.trim().isEmpty()) ? location.trim() : null;

        List<Service> services = serviceRepository.searchServices(cleanKeyword, cleanLocation);

        return services.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDto findServiceById(int id) {
        Service service = serviceRepository.findById(id).orElse(null);
        return service != null ? mapToDto(service) : null;
    }

    private ServiceDto mapToDto(Service service) {
        return ServiceDto.builder()
                .serviceid(service.getServiceid())
                .numeServiciu(service.getNumeServiciu())
                .pret(service.getPret())
                .photourl(service.getPhotourl())
                .locatie(service.getLocatie())
                .createdon(service.getCreatedon())
                .build();
    }
}