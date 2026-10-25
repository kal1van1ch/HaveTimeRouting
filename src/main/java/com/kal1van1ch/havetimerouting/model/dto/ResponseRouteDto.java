package com.kal1van1ch.havetimerouting.model.dto;

import com.kal1van1ch.havetimerouting.model.RouteProfile;

import java.util.List;

public record ResponseRouteDto(
        Double routeDistance,
        Long routeTime,
        RouteProfile profile,
        List<RoutePointDto> points
){}
