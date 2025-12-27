package com.irctc.service;

import com.irctc.dto.TrainRouteDto;
import com.irctc.entity.Train;

import java.util.List;

public interface TrainRouteService {

    TrainRouteDto addRoute(TrainRouteDto trainRouteDto);

    List<TrainRouteDto> getRoutesByTrain(Long id);

    TrainRouteDto updateRoute(Long id, TrainRouteDto trainRouteDto);

    void deleteRoute(Long id);
}
