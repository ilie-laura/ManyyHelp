package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.ProgramareDto;
import java.time.LocalDateTime;

public interface ProgramareService {
    ProgramareDto createProgramare(Long userId, int serviceId, int providerId, LocalDateTime dataProgramare);
}