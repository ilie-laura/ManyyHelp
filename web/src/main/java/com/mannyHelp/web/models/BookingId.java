package com.mannyHelp.web.models;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookingId implements Serializable {
    private int userid;
    private int serviceid;
    private int providerid;

//    public BookingId() {}
//
//    public BookingId(int userid, int serviceid, int providerid) {
//        this.userid = userid;
//        this.serviceid = serviceid;
//        this.providerid = providerid;
//    }

    // getters/setters

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof BookingId)) return false;
//        BookingId that = (BookingId) o;
//        return userid == that.userid &&
//                serviceid == that.serviceid &&
//                providerid == that.providerid;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, serviceid, providerid);
    }
}