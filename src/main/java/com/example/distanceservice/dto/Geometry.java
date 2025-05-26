package com.example.distanceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {
    private double lat;

    @JsonProperty("lng")
    private double lng;

    // Геттеры и сеттеры
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}