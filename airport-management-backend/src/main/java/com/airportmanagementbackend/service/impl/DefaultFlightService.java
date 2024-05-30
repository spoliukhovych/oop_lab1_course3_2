package com.airportmanagementbackend.service.impl;

import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.dao.FlightDao;
import com.airportmanagementbackend.dao.impl.DefaultFlightDao;
import com.airportmanagementbackend.model.Flight;
import com.airportmanagementbackend.service.FlightService;
import com.airportmanagementbackend.service.mapper.FlightMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class DefaultFlightService implements FlightService {

  private final FlightDao flightDao;
  private final FlightMapper mapper;

  public DefaultFlightService() {
    this(new DefaultFlightDao());
  }

  public DefaultFlightService(FlightDao flightDao) {
    this.flightDao = flightDao;
    this.mapper = Mappers.getMapper(FlightMapper.class);
  }

  @Override
  public List<FlightDto> findAll() {
    return flightDao.findAll().stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<FlightDto> findAllByCrewMemberId(Long crewMemberId) {
    return flightDao.findAllByCrewMemberId(crewMemberId).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<FlightDto> findById(Long id) {
    return flightDao.findById(id).map(mapper::toDto);
  }

  @Override
  public FlightDto create(FlightDto toCreate) {
    Flight entity = mapper.toEntity(toCreate);

    return mapper.toDto(flightDao.insert(entity));
  }

  @Override
  public FlightDto update(FlightDto toUpdate) {
    Flight entity = mapper.toEntity(toUpdate);

    return mapper.toDto(flightDao.update(entity));
  }

  @Override
  public boolean delete(Long id) {
    return flightDao.delete(id);
  }

}
