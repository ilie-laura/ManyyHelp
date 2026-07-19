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
    @GeneratedValue
    private int userid;
    private String username;
    private String password;
    private String photourl;

    private String nume;
    private String prenume;

    private Boolean user_or_provider;//0-user 1-provider


    public String getPhotoUrl() {
        return  photourl;
    }
}
