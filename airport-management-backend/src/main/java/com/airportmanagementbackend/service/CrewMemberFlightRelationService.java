package com.airportmanagementbackend.service;

import com.airportmanagementbackend.common.dto.relation.CrewMemberFlightRelationDto;

public interface CrewMemberFlightRelationService {

  boolean create(CrewMemberFlightRelationDto toCreate);

  boolean exists(CrewMemberFlightRelationDto relation);

  boolean delete(CrewMemberFlightRelationDto relation);

}
