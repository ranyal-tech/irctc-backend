package com.irctc.controllers.admin;

import com.irctc.dto.TrainDTO;
import com.irctc.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
public class TrainController {

    private TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping
    public ResponseEntity<TrainDTO> create(
            @RequestBody TrainDTO trainDTO
    ){
       return new ResponseEntity<>(trainService.createTrain(trainDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TrainDTO> getAllTrains(){
        return trainService.getAllTrains();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainDTO> getTrainById(
            @PathVariable Long id
    ){
        TrainDTO trainDTO=trainService.getTrainById(id);
        return new ResponseEntity<>(trainDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainDTO> updateTrain(
            @PathVariable Long id,
            @RequestBody TrainDTO trainDTO
    ){
        return new ResponseEntity<>(trainService.updateTrain(id, trainDTO),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTrain(
                @PathVariable Long id
    ){
        trainService.deleteTrain(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
