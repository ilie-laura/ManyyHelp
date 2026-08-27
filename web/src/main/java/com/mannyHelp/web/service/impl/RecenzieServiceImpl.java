package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.models.Recenzie;
import com.mannyHelp.web.models.Service;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.RecenzieRepository;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.RecenzieService;


import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class RecenzieServiceImpl implements RecenzieService {

    private final RecenzieRepository recenzieRepository;
    private final UsersRepository usersRepository;
    private final OferitorServiciiRepository oferitorRepository;
    private final ServiceRepository serviceRepository;

    public RecenzieServiceImpl(RecenzieRepository recenzieRepository,
                               UsersRepository usersRepository,
                               OferitorServiciiRepository oferitorRepository,
                               ServiceRepository serviceRepository) {
        this.recenzieRepository = recenzieRepository;
        this.usersRepository = usersRepository;
        this.oferitorRepository = oferitorRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void addRecenzie(Long userId, Long providerId, Integer serviceId, int rating, String comment) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        OferitorServicii provider = oferitorRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Furnizorul nu a fost găsit"));


        Service service = null;
        if (serviceId != null) {
            service = serviceRepository.findById(serviceId).orElse(null);
        }

        Recenzie recenzie = Recenzie.builder()
                .user(user)
                .provider(provider)
                .service(service)
                .rating(rating)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();

        recenzieRepository.save(recenzie);
    }

    @Override
    public List<Recenzie> getRecenziiByService(int serviceId) {
        return recenzieRepository.findByServiceServiceidOrderByCreatedAtDesc(serviceId);
    }

    @Override
    public double getAverageRatingByService(int serviceId) {
        List<Recenzie> reviews = getRecenziiByService(serviceId);
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Recenzie r : reviews) {
            sum += r.getRating();
        }
        return Math.round((sum / reviews.size()) * 10.0) / 10.0;
    }

    @Override
    public List<Recenzie> getRecenziiByProvider(Long providerId) {
        if (providerId == null) return List.of();

        List<Recenzie> reviews = recenzieRepository.findByProviderProviderid(providerId);

        if (reviews.isEmpty()) {
            reviews = recenzieRepository.findByProviderUserUserid(providerId);
        }

        return reviews;
    }

    @Override
    public void addProviderResponse(Integer reviewId, String responseText) {
        Recenzie recenzie = recenzieRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Recenzia nu a fost găsită!"));

        recenzie.setProviderResponse(responseText);
        recenzie.setResponseCreatedAt(LocalDateTime.now());
        recenzieRepository.save(recenzie);
    }

    @Override
    public double getAverageRatingByProvider(Long providerId) {
        List<Recenzie> reviews = getRecenziiByProvider(providerId);
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Recenzie r : reviews) {
            sum += r.getRating();
        }
        return Math.round((sum / reviews.size()) * 10.0) / 10.0;
    }

    @Override
    public List<Recenzie> getRecenziiByUserId(Long userId, Integer limit) {
        if (userId == null) {
            return List.of();
        }

        int actualLimit = (limit != null && limit > 0) ? limit : 3;
        return recenzieRepository.findByUserUseridOrderByCreatedAtDesc(userId, actualLimit);
    }

    @Override
    public List<Recenzie> getRecentPlatformReviews(int limit) {
        return recenzieRepository.findRecentPlatformReviews(limit > 0 ? limit : 3);
    }
}