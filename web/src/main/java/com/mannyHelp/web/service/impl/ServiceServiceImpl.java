package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.models.Service;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.ServiceService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UsersRepository usersRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository, UsersRepository usersRepository) {
        this.serviceRepository = serviceRepository;
        this.usersRepository = usersRepository;
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
    @Override
    public void saveService(ServiceDto serviceDto) {


        Users provider = usersRepository.findById(serviceDto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));


        Service service = new Service();
        service.setNumeServiciu(serviceDto.getNumeServiciu());
        service.setPret(serviceDto.getPret());
        service.setPhotourl(serviceDto.getPhotourl());
        service.setLocatie(serviceDto.getLocatie());
        service.setCategorie(serviceDto.getCategorie());
        service.setCreatedon(LocalDateTime.now());


        service.setProvider(provider);


        serviceRepository.save(service);
    }
}