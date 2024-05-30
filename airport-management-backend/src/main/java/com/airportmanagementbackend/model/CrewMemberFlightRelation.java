package com.airportmanagementbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrewMemberFlightRelation {

  private Long id;
  private Long crewMemberId;
  private Long flightId;

  public CrewMemberFlightRelation(Long crewMemberId, Long flightId) {
    this.crewMemberId = crewMemberId;
    this.flightId = flightId;
  }

}
