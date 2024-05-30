package com.airportmanagementbackend.common.dto.flight;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightListDto {

  private List<FlightDto> flights;

}
