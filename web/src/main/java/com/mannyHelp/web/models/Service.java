package com.mannyHelp.web.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Service {
    @Id
    @GeneratedValue
    private int serviceid;
    private String numeServiciu;
    private Double pret;
    private String photourl;
    private String Locatie;


    @CreationTimestamp
    private LocalDateTime createdon;


}
