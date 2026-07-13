package com.mannyHelp.web.dto;

import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.models.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecenzieDto {

 private String numeuser;           // cine a scris recenzia

private String providernume;   // cui i se adresează recenzia


    private int rating;             // ex: 1-5
    private String comment;
    private LocalDateTime createdAt;
}
