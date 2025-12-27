package com.irctc.controller.admin;

import com.irctc.dto.TrainRouteDto;
import com.irctc.service.TrainRouteService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-routes")
public class TrainRouteController {

    private TrainRouteService trainRouteService;

    public TrainRouteController(TrainRouteService trainRouteService) {
        this.trainRouteService = trainRouteService;
    }

    @PostMapping
    public ResponseEntity<TrainRouteDto> createRoute(
            @RequestBody TrainRouteDto trainRouteDto
    ){
        return new ResponseEntity<>(trainRouteService.addRoute(trainRouteDto), HttpStatus.CREATED);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TrainRouteDto>> getRoutesByTrain(
            @PathVariable Long trainId
    ){
       List<TrainRouteDto> routes= trainRouteService.getRoutesByTrain(trainId);
        return ResponseEntity.ok(routes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainRouteDto> updateTrainRoute(
            @PathVariable Long id,
            @RequestBody TrainRouteDto trainRouteDto
    )
    {
        TrainRouteDto updatedRoute=this.trainRouteService.updateRoute(id, trainRouteDto);
        return ResponseEntity.ok(updatedRoute);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainRoute(
            @PathVariable Long id
    ){
        this.trainRouteService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
