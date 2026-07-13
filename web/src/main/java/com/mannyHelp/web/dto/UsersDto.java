package com.mannyHelp.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UsersDto {
    private String username;
    private String photourl;

    private String nume;
    private String prenume;

}
