package com.mannyHelp.web.dto;

import com.mannyHelp.web.models.Recenzie;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OferitorServiciiDto {

    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String photoUrl;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long providerid;
    private String numeCompanie;
    private String cui;
    private String descriereServicii;
    private String telefonContact;

    @Builder.Default
    private List<Recenzie> reviews = new ArrayList<>();
}