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
    @GeneratedValue
    private int providerid;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzie> reviews = new ArrayList<>();


}
