package com.airportmanagementbackend.service.impl;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.dao.CrewMemberDao;
import com.airportmanagementbackend.dao.CrewMemberFlightRelationDao;
import com.airportmanagementbackend.dao.impl.DefaultCrewMemberDao;
import com.airportmanagementbackend.dao.impl.DefaultCrewMemberFlightRelationDao;
import com.airportmanagementbackend.model.CrewMember;
import com.airportmanagementbackend.service.CrewMemberService;
import com.airportmanagementbackend.service.mapper.CrewMemberMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

public class DefaultCrewMemberService implements CrewMemberService {

  private final CrewMemberDao crewMemberDao;
  private final CrewMemberMapper mapper;

  public DefaultCrewMemberService() {
    this(new DefaultCrewMemberDao());
  }

  public DefaultCrewMemberService(CrewMemberDao crewMemberDao) {
    this.crewMemberDao = crewMemberDao;
    this.mapper = Mappers.getMapper(CrewMemberMapper.class);
  }

  @Override
  public List<CrewMemberDto> findAll() {
    return crewMemberDao.findAll().stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<CrewMemberDto> findAllByFlightId(Long flightId) {
    return crewMemberDao.findAllByFlightId(flightId).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<CrewMemberDto> findById(Long id) {
    return crewMemberDao.findById(id).map(mapper::toDto);
  }

  @Override
  public CrewMemberDto create(CrewMemberDto toCreate) {
    CrewMember entity = mapper.toEntity(toCreate);

    return mapper.toDto(crewMemberDao.insert(entity));
  }

  @Override
  public CrewMemberDto update(CrewMemberDto toUpdate) {
    CrewMember entity = mapper.toEntity(toUpdate);

    return mapper.toDto(crewMemberDao.update(entity));
  }

  @Override
  public boolean delete(Long id) {
    return crewMemberDao.delete(id);
  }

}
