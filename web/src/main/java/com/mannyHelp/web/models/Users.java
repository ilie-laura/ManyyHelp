package com.mannyHelp.web.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- Adaugă (strategy = GenerationType.IDENTITY)
    private Long userid;
    private String username;
    private String password;
    private String photourl;

    private String nume;
    private String prenume;
    @Column(name = "user_or_provider")
    private boolean userOrProvider;

    public String getPhotoUrl() {
        return  photourl;
    }


}
