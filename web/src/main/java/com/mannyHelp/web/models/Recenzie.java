package com.mannyHelp.web.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

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


    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private String providerResponse;
    private LocalDateTime responseCreatedAt;

}