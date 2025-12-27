package com.irctc.service.impl;

import com.irctc.dto.TrainRouteDto;
import com.irctc.entity.Station;
import com.irctc.entity.Train;
import com.irctc.entity.TrainRoute;
import com.irctc.exceptions.ResourceNotFoundException;
import com.irctc.repository.StationRepo;
import com.irctc.repository.TrainRespository;
import com.irctc.repository.TrainRouteRepository;
import com.irctc.service.TrainRouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainRouteServiceImpl implements TrainRouteService {

    private StationRepo stationRepo;
    private TrainRespository trainRespository;
    private TrainRouteRepository trainRouteRepository;
    private ModelMapper modelMapper;

    public TrainRouteServiceImpl(StationRepo stationRepo, TrainRespository trainRespository, TrainRouteRepository trainRouteRepository, ModelMapper modelMapper) {
        this.stationRepo = stationRepo;
        this.trainRespository = trainRespository;
        this.trainRouteRepository = trainRouteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainRouteDto addRoute(TrainRouteDto trainRouteDto) {
        Train train=trainRespository.findById(trainRouteDto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train not found with id : "+ trainRouteDto.getTrain().getId()));
        Station station= stationRepo.findById(trainRouteDto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station not found with id:"+ trainRouteDto.getStation().getId()));
        TrainRoute trainRoute=modelMapper.map(trainRouteDto,TrainRoute.class);
        trainRoute.setTrain(train);
        trainRoute.setStation(station);
        TrainRoute updatedTrainRoute=trainRouteRepository.save(trainRoute);
        return modelMapper.map(updatedTrainRoute, TrainRouteDto.class);
    }

    @Override
    public List<TrainRouteDto> getRoutesByTrain(Long id) {
        Train train=trainRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train not found with id : "+ id));
        List<TrainRoute> trainRoutes=this.trainRouteRepository.findByTrain(id);
        return trainRoutes.stream().map(trainRoute -> modelMapper.map(trainRoute,TrainRouteDto.class)).toList();
    }

    @Override
    public TrainRouteDto updateRoute(Long id, TrainRouteDto trainRouteDto) {
        TrainRoute trainRoute=trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Route not found"));
        Train train=trainRespository.findById(trainRouteDto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train not found with id : "+ trainRouteDto.getTrain().getId()));
        Station station= stationRepo.findById(trainRouteDto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station not found with id:"+ trainRouteDto.getStation().getId()));
        trainRoute.setTrain(train);
        trainRoute.setArrivalTime(trainRouteDto.getArrivalTime());
        trainRoute.setHaltMinutes(trainRoute.getHaltMinutes());
        trainRoute.setDepartureTime(trainRouteDto.getDepartureTime());
        trainRoute.setStationOrder(trainRoute.getStationOrder());
        trainRoute.setStation(station);
        trainRoute.setDistanceFromSource(trainRouteDto.getDistanceFromSource());

        TrainRoute updatedTrainRoute=trainRouteRepository.save(trainRoute);
        return modelMapper.map(updatedTrainRoute,TrainRouteDto.class);
    }

    @Override
    public void deleteRoute(Long id) {

        TrainRoute trainRoute=trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Route not found"));

        trainRouteRepository.delete(trainRoute);

    }
}
