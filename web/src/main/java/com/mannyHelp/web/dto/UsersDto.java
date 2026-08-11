package com.mannyHelp.web.dto;

import com.mannyHelp.web.models.Users;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String username;
    private String photourl;
    private Boolean userOrProvider;
    private String nume;
    private String prenume;
    private String password;

    private String numeCompanie;
    private String cui;
    private String telefonContact;
    private String descriereServicii;

    public Boolean getUserOrProvider() {
        return userOrProvider;
    }

    public boolean isUserOrProvider() {
        return userOrProvider;
    }
}