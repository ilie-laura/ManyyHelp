package com.mannyHelp.web.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class ServiceDto {

    private String numeServiciu;
    private Double pret;
    private String photourl;
    private String Locatie;

    @CreationTimestamp
    private LocalDateTime createdon;
}
