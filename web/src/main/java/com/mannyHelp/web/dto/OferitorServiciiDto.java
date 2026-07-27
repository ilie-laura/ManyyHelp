package com.mannyHelp.web.dto;

import com.mannyHelp.web.models.Recenzie;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OferitorServiciiDto {

    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String photoUrl;


    private String numeCompanie;
    private String cui;
    private String descriereServicii;
    private String telefonContact;
    private String photourl;
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzie> reviews = new ArrayList<>();
}
