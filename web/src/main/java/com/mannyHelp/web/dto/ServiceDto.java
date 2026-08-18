package com.mannyHelp.web.dto;

import com.mannyHelp.web.models.OferitorServicii;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
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


    private Double rating;
    private Long providerId;


}