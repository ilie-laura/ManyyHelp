package com.mannyHelp.web.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Long userid;
    private String username;
    private String photourl;
    private boolean userOrProvider;
    private String nume;
    private String prenume;
    private String password;
}