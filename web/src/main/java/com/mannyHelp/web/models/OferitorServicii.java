package com.mannyHelp.web.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class OferitorServicii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long providerid;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzie> reviews = new ArrayList<>();
    private String numeCompanie;
    private String cui;
    private String descriereServicii;
    private String telefonContact;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private Users user;

}
