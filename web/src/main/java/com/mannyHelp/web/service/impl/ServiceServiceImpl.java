package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.models.Service;
import com.mannyHelp.web.models.ServiceSpecifications;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.RecenzieService;
import com.mannyHelp.web.service.ServiceService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public
class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UsersRepository usersRepository;
    private final RecenzieService recenzieService;

    public ServiceServiceImpl(ServiceRepository serviceRepository, UsersRepository usersRepository, RecenzieService recenzieService) {
        this.serviceRepository = serviceRepository;
        this.usersRepository = usersRepository;
        this.recenzieService = recenzieService;
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

    @Override
    public List<ServiceDto> findServicesByProviderUserId(Long userId) {
        List<Service> services = serviceRepository.findByProviderUserid(userId);
        return services.stream().map(this::mapToDto).toList();
    }
    @Override
    public List<ServiceDto> searchAndFilterServices(String keyword, String location, String category, String sortBy) {
        Specification<Service> spec = ServiceSpecifications.withFilters(keyword, location, category);
        List<Service> entities = serviceRepository.findAll(spec);

        List<ServiceDto> dtoList = entities.stream()
                .map(entity -> {
                    ServiceDto dto = mapToDto(entity);


                    if (entity.getProvider() != null) {
                        Double avgRating = recenzieService.getAverageRatingByProvider(entity.getProvider().getUserid());
                        dto.setRating(avgRating != null ? avgRating : 0.0);
                    } else {
                        dto.setRating(0.0);
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // Sortare după criteriul selectat
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            switch (sortBy) {
                case "rating_desc":
                    dtoList.sort(Comparator.comparingDouble((ServiceDto s) -> s.getRating() != null ? s.getRating() : 0.0).reversed());
                    break;
                case "price_asc":
                    dtoList.sort(Comparator.comparingDouble(s -> s.getPret() != null ? s.getPret() : Double.MAX_VALUE));
                    break;
                case "price_desc":
                    dtoList.sort(Comparator.comparingDouble((ServiceDto s) -> s.getPret() != null ? s.getPret() : 0.0).reversed());
                    break;
                case "newest":
                    dtoList.sort(Comparator.comparingInt(ServiceDto::getServiceid).reversed());
                    break;
            }
        }

        return dtoList;
    }



}