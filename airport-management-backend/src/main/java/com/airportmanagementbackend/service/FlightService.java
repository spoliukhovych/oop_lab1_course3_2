package com.airportmanagementbackend.service;

import com.airportmanagementbackend.common.dto.flight.FlightDto;
import java.util.List;
import java.util.Optional;

public interface FlightService {

  List<FlightDto> findAll();

  List<FlightDto> findAllByCrewMemberId(Long crewMemberId);

  Optional<FlightDto> findById(Long id);

  FlightDto create(FlightDto toCreate);

  FlightDto update(FlightDto toUpdate);

  boolean delete(Long id);

}
