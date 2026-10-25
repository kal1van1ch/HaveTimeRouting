package com.kal1van1ch.havetimerouting.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.kal1van1ch.havetimerouting.exception.RouteBuildErrorException;
import com.kal1van1ch.havetimerouting.model.dto.RequestRouteDto;
import com.kal1van1ch.havetimerouting.model.dto.ResponseRouteDto;
import com.kal1van1ch.havetimerouting.model.dto.RoutePointDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteBuildService {

    private final GraphHopper graphHopper;

    public RouteBuildService(
            GraphHopper graphHopper
    ){
        this.graphHopper = graphHopper;
    }

    public ResponseRouteDto buildRoute(RequestRouteDto requestRouteDto){
        GHRequest ghRequest = new GHRequest(
                requestRouteDto.startLat(),
                requestRouteDto.startLon(),
                requestRouteDto.endLat(),
                requestRouteDto.endLon()
        );
        ghRequest.setProfile(requestRouteDto.profile().getProfile());

        GHResponse ghResponse = graphHopper.route(ghRequest);

        if (ghResponse.hasErrors()){
            throw new RouteBuildErrorException(
                    "Route building error",
                    ghResponse.getErrors().getFirst()
            );
        }

        ResponsePath responsePath = ghResponse.getBest();

        PointList allPointsGH = responsePath.getPoints();
        List<RoutePointDto> points = new ArrayList<>(allPointsGH.size());

        for (int i = 0; i < allPointsGH.size(); i++){
            points.add(
                    new RoutePointDto(
                            allPointsGH.getLat(i),
                            allPointsGH.getLon(i)
                    )
            );
        }

        return new ResponseRouteDto(
                responsePath.getDistance(),
                responsePath.getTime() / 1000,
                requestRouteDto.profile(),
                points
        );
    }
}
