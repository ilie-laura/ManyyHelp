package com.mannyHelp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private int serviceid;
    private String numeServiciu;
    private Double pret;
    private String photourl;
    private String locatie;
    private LocalDateTime createdon;
    private String categorie;

    private Long providerId;
}