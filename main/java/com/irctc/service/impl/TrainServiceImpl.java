package com.irctc.service.impl;

import com.irctc.dto.TrainDTO;
import com.irctc.entity.Station;
import com.irctc.entity.Train;
import com.irctc.exceptions.ResourceNotFoundException;
import com.irctc.repository.StationRepo;
import com.irctc.repository.TrainRespository;
import com.irctc.service.TrainService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    private ModelMapper modelMapper;
    private StationRepo stationRepo;
    private TrainRespository trainRespository;

    public TrainServiceImpl(ModelMapper modelMapper, StationRepo stationRepo, TrainRespository trainRespository) {
        this.modelMapper = modelMapper;
        this.stationRepo = stationRepo;
        this.trainRespository = trainRespository;
    }

    @Override
    public TrainDTO createTrain(TrainDTO trainDTO) {
        Station sourceStation = stationRepo.findById(trainDTO.getSourceStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with the id:" + trainDTO.getSourceStation().getId()));
        Station destinationStation=stationRepo.findById(trainDTO.getDestinationStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with the id:" + trainDTO.getDestinationStation().getId()));
       Train train=modelMapper.map(trainDTO,Train.class);
       train.setSourceStation(sourceStation);
       train.setDestinationStation(destinationStation);
       Train savedTrain=trainRespository.save(train);
        return modelMapper.map(savedTrain, TrainDTO.class);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<Train> trains=trainRespository.findAll();
        return trains.stream().map(train-> modelMapper.map(train, TrainDTO.class)).toList();
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        Train train=trainRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train not found with id:"+ id));
        return modelMapper.map(train, TrainDTO.class);
    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        Train existingTrain=trainRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train not found with id:"+ id));
        existingTrain.setName(trainDTO.getName());
        existingTrain.setNumber(trainDTO.getNumber());
        existingTrain.setTotalDistance(trainDTO.getTotalDistance());

        Station sourceStation = stationRepo.findById(trainDTO.getSourceStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with the id:" + trainDTO.getSourceStation().getId()));
        Station destinationStation=stationRepo.findById(trainDTO.getDestinationStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with the id:" + trainDTO.getDestinationStation().getId()));

        existingTrain.setSourceStation(sourceStation);
        existingTrain.setDestinationStation(destinationStation);
       Train updatedTrain= trainRespository.save(existingTrain);
         return modelMapper.map(updatedTrain, TrainDTO.class);
    }

    @Override
    public void deleteTrain(Long id) {
        Train existingTrain=trainRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train not found with id:"+ id));
            trainRespository.delete(existingTrain);
    }
}
