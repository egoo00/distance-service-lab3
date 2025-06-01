package com.example.distanceservice.dto;

public class DistanceResponse {
    private String fromCity;
    private String toCity;
    private double distance;
    private String unit;

    public DistanceResponse(String fromCity, String toCity, double distance, String unit) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
        this.unit = unit;
    }

    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public double getDistance() { return distance; }
    public String getUnit() { return unit; }
}
