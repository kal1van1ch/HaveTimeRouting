package com.kal1van1ch.havetimerouting.model.dto;

import com.kal1van1ch.havetimerouting.model.RouteProfile;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RequestRouteDto(
        @NotNull
        @DecimalMin("-90")
        @DecimalMax("90")
        Double startLat,

        @NotNull
        @DecimalMin("-180")
        @DecimalMax("180")
        Double startLon,

        @NotNull
        @DecimalMin("-90")
        @DecimalMax("90")
        Double endLat,

        @NotNull
        @DecimalMin("-180")
        @DecimalMax("180")
        Double endLon,

        RouteProfile profile
) {
    public RequestRouteDto {
        if (profile == null){
            profile = RouteProfile.FOOT;
        }
    }
}
