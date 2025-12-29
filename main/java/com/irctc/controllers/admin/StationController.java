package com.irctc.controllers.admin;

import com.irctc.config.AppConstants;
import com.irctc.dto.PagedResponse;
import com.irctc.dto.StationDto;
import com.irctc.service.StationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/stations")
public class StationController {

    private StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationDto> createStation(
            @Valid @RequestBody StationDto stationDto
        ) {
        StationDto dto = stationService.createStation(stationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public PagedResponse<StationDto> listStations(
            @RequestParam(value="page",defaultValue = AppConstants.DEFAULT_PAGE) int page,
            @RequestParam(value="size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir

    ){
        return stationService.listStations(
                page,
                size,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/{id}")
    public StationDto getById(
            @PathVariable Long id
    ){
       return  stationService.getById(id);
    }

    @PutMapping("/{id}")
    public StationDto update(
            @PathVariable Long id,
            @RequestBody StationDto stationDto
    ){
      return stationService.update(id,stationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
      return  stationService.delete(id);
    }
}
