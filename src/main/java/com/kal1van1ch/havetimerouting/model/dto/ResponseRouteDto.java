package com.kal1van1ch.havetimerouting.model.dto;

import com.kal1van1ch.havetimerouting.model.RouteProfile;

import java.util.List;

public record ResponseRouteDto(
        Double routeDistanceMeters,
        Long routeTimeSeconds,
        RouteProfile profile,
        List<RoutePointDto> points
){}
