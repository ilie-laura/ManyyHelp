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
@Table
public class Programare {
   @EmbeddedId
    private BookingId programareid;
    @ManyToOne
    @MapsId("userid")           // leagă câmpul userid din BookingId
    @JoinColumn(name = "userid")
    private Users user;

    @ManyToOne
    @MapsId("serviceid")        // leagă câmpul serviceid din BookingId
    @JoinColumn(name = "serviceid")
    private Service service;

    @ManyToOne
    @MapsId("providerid")       // leagă câmpul providerid din BookingId
    @JoinColumn(name = "providerid")
    private OferitorServicii provider;

    private String status;
    private java.time.LocalDateTime dataProgramare;
}
