package com.irctc.service.impl;

import com.irctc.dto.PagedResponse;
import com.irctc.dto.StationDto;
import com.irctc.entity.Station;
import com.irctc.exceptions.ResourceNotFoundException;
import com.irctc.repository.StationRepo;
import com.irctc.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    private StationRepo stationRepo;
    private ModelMapper modelMapper;

    public StationServiceImpl(StationRepo stationRepo, ModelMapper modelMapper) {
        this.stationRepo = stationRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public StationDto createStation(StationDto stationDto) {

       Station station= modelMapper.map(stationDto, Station.class);
       Station savedStation=stationRepo.save(station);
        return modelMapper.map(savedStation,StationDto.class);
    }

    @Override
    public PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir) {
        Sort sort= sortDir.trim().equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page,size,sort);
         Page<Station> stations= stationRepo.findAll(pageable);
        Page<StationDto> stationDtos= stations.map(station->modelMapper.map(station,StationDto.class));
        return PagedResponse.fromPage(stationDtos);
    }

    @Override
    public StationDto getById(Long id) {
     Station station=   stationRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Station with that Id is not present"));
    return modelMapper.map(station,StationDto.class);
    }

    @Override
    public StationDto update(Long id, StationDto stationDto) {
        Station station=   stationRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Station with that Id is not present"));

        station.setName(stationDto.getName());
        station.setCode(stationDto.getCode());
        station.setCity(stationDto.getCity());
        station.setState(stationDto.getState());

        Station updatedStation=stationRepo.save(station);
        return modelMapper.map(updatedStation, StationDto.class);

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Station station=   stationRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Station with that Id is not present"));
        stationRepo.delete(station);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
