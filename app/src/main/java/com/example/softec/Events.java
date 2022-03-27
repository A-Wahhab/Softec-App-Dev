package com.example.softec;

import java.text.DateFormat;

public class Events {
    String name, venue, sdate, edate;

    public Events(String name, String venue, String sdate, String edate) {
        this.name = name;
        this.venue = venue;
        this.sdate = sdate;
        this.edate = edate;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getSdate() {
        return sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
