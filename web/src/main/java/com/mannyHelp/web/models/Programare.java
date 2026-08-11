package com.mannyHelp.web.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Programare {

 @EmbeddedId
 private BookingId programareid;

 @ManyToOne
 @MapsId("userId")
 @JoinColumn(name = "userid")
 private Users user;

 @ManyToOne
 @MapsId("serviceId")
 @JoinColumn(name = "serviceid")
 private Service service;

 @ManyToOne
 @MapsId("providerId")
 @JoinColumn(name = "providerid")
 private OferitorServicii provider;

 private String status;
 private LocalDateTime dataProgramare;
}