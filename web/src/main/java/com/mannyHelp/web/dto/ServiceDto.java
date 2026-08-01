package com.mannyHelp.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServiceDto {
    private int serviceid;
    private String numeServiciu;
    private Double pret;
    private String photourl;
    private String locatie;
    private LocalDateTime createdon;
}