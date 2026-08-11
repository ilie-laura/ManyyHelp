package com.mannyHelp.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramareDto {
    private String providerNume;
    private String userNume;
    private String serviceNume;
    private String status;
    private Long userId;
    private Long providerId;
    private Long serviceId;

    private java.time.LocalDateTime dataProgramare;
}
