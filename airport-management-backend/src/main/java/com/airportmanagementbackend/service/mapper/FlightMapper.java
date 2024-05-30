package com.airportmanagementbackend.service.mapper;

import com.airportmanagementbackend.common.dto.flight.FlightDto;
import com.airportmanagementbackend.model.Flight;
import org.mapstruct.Mapper;

@Mapper
public abstract class FlightMapper {

  public abstract FlightDto toDto(Flight entity);

  public abstract Flight toEntity(FlightDto dto);

}
