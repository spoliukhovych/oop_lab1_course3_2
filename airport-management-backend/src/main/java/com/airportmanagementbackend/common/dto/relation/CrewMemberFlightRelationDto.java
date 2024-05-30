package com.airportmanagementbackend.common.dto.relation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrewMemberFlightRelationDto {

  private Long crewMemberId;
  private Long flightId;

}
