package com.mannyHelp.web.service;

import com.mannyHelp.web.models.Recenzie;
import java.util.List;

public interface RecenzieService {

    void addRecenzie(Long userId, Long providerId, Integer serviceId, int rating, String comment);


    List<Recenzie> getRecenziiByService(int serviceId);
    double getAverageRatingByService(int serviceId);


    List<Recenzie> getRecenziiByProvider(Long providerId);
    double getAverageRatingByProvider(Long providerId);
    List<Recenzie> getRecenziiByUserId(Long userId, Integer limit);
    List<Recenzie> getRecentPlatformReviews(int limit);
    void addProviderResponse(Integer reviewId, String responseText);
}