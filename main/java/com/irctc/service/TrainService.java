package com.irctc.service;

import com.irctc.dto.TrainDTO;

import java.util.List;

public interface TrainService {

    public TrainDTO createTrain(TrainDTO trainDTO);
    public List<TrainDTO> getAllTrains();
    public TrainDTO getTrainById(Long id);
    public TrainDTO updateTrain(
            Long id,
            TrainDTO trainDTO
    );
    public void deleteTrain(Long id);


}
