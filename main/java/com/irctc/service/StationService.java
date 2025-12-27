package com.irctc.service;

import com.irctc.dto.PagedResponse;
import com.irctc.dto.StationDto;
import org.springframework.http.ResponseEntity;

public interface StationService {

    StationDto createStation (StationDto stationDto);

    PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir);

    StationDto getById(Long id);

    StationDto update(Long id, StationDto stationDto);

    ResponseEntity<Void> delete(Long id);
}
