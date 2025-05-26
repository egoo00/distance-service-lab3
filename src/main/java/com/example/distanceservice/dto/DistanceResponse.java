package com.example.distanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistanceResponse {
    private final String from;
    private final String to;
    private final double distance;
    private final String unit;
}