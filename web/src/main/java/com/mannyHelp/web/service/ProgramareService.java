package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.ProgramareDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ProgramareService {
    ProgramareDto createProgramare(Long userId, int serviceId,Long providerId, LocalDateTime dataProgramare);
    List<ProgramareDto> getProgramariByUserId(Long userId);
    List<ProgramareDto> getProgramariByProviderUserId(Long providerUserId);
}