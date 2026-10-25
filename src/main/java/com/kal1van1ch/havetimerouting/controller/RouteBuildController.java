package com.kal1van1ch.havetimerouting.controller;

import com.kal1van1ch.havetimerouting.model.dto.RequestRouteDto;
import com.kal1van1ch.havetimerouting.model.dto.ResponseRouteDto;
import com.kal1van1ch.havetimerouting.service.RouteBuildService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteBuildController {

    private final RouteBuildService routeBuildService;

    public RouteBuildController(
            RouteBuildService routeBuildService
    ){
        this.routeBuildService = routeBuildService;
    }

    @Operation(
            summary = "Построить маршрут",
            description = """
                Строит кратчайший по расстоянию маршрут между начальной
                и конечной точками по дорожной сети OpenStreetMap.

                Поддерживает пешие маршруты (FOOT) и автомобильные (CAR).
                Если профиль не указан, используется FOOT.

                Возвращает расстояние в метрах, приблизительное время
                в секундах и последовательность координат маршрута.
                """
    )
    @PostMapping("api/v1/routes")
    public ResponseEntity<ResponseRouteDto> routeBuilder(
            @Valid @RequestBody RequestRouteDto requestRouteDto
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        routeBuildService.buildRoute(requestRouteDto)
                );
    }
}
