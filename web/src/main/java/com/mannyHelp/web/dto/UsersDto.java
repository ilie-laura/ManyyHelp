package com.mannyHelp.web.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDto {
    private String username;
    private String photourl;
   private boolean user_or_provider;
    private String nume;
    private String prenume;

}
