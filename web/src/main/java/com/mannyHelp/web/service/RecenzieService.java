package com.mannyHelp.web.service;

import com.mannyHelp.web.models.Recenzie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecenzieService {
    void addRecenzie(Long userId, Long providerId, int rating, String comment);
    List<Recenzie> getRecenziiByProvider(Long providerId);
    void addProviderResponse(Integer reviewId, String responseText);
    double getAverageRatingByProvider(Long providerId);
}
