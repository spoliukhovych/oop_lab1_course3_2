package com.airportmanagementbackend.dao;

import com.airportmanagementbackend.model.CrewMemberFlightRelation;

public interface CrewMemberFlightRelationDao {

  CrewMemberFlightRelation insert(CrewMemberFlightRelation relation);

  boolean existsByCrewMemberAndFlightIds(Long crewMemberId, Long flightId);

  boolean deleteByCrewMemberAndFlightIds(Long crewMemberId, Long flightId);

}
