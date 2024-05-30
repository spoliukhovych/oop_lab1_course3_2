package com.airportmanagementbackend.service;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import java.util.List;
import java.util.Optional;

public interface CrewMemberService {

  List<CrewMemberDto> findAll();

  List<CrewMemberDto> findAllByFlightId(Long flightId);

  Optional<CrewMemberDto> findById(Long id);

  CrewMemberDto create(CrewMemberDto toCreate);

  CrewMemberDto update(CrewMemberDto toUpdate);

  boolean delete(Long id);

}
