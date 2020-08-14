package com.example.l12ws;

import java.io.Serializable;
import java.util.Date;

public class Incident implements Serializable {

    private Date date;
    private String latitude;
    private String longtitude;
    private String message;
    private String type;

    public Incident(){

    }

    public Incident(Date date, String latitude, String longtitude, String message, String type) {
        this.date = date;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.message = message;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
