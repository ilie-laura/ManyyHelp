package com.mannyHelp.web.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recenzie")
public class Recenzie{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private Users user;              // cine a scris recenzia

    @ManyToOne
    @JoinColumn(name = "providerid")
    private OferitorServicii provider;      // cui i se adresează recenzia


    private int rating;             // ex: 1-5
    private String comment;
    private LocalDateTime createdAt;

    // getters/setters
}