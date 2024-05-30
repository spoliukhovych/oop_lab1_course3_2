package com.airportmanagementbackend.service.impl;

import com.airportmanagementbackend.common.dto.relation.CrewMemberFlightRelationDto;
import com.airportmanagementbackend.dao.CrewMemberFlightRelationDao;
import com.airportmanagementbackend.dao.impl.DefaultCrewMemberFlightRelationDao;
import com.airportmanagementbackend.service.CrewMemberFlightRelationService;
import com.airportmanagementbackend.service.mapper.CrewMemberFlightRelationMapper;
import org.mapstruct.factory.Mappers;

public class DefaultCrewMemberFlightRelationService implements CrewMemberFlightRelationService {

  private final CrewMemberFlightRelationDao relationDao;
  private final CrewMemberFlightRelationMapper mapper;

  public DefaultCrewMemberFlightRelationService() {
    this(new DefaultCrewMemberFlightRelationDao());
  }

  public DefaultCrewMemberFlightRelationService(CrewMemberFlightRelationDao relationDao) {
    this.relationDao = relationDao;
    this.mapper = Mappers.getMapper(CrewMemberFlightRelationMapper.class);
  }

  @Override
  public boolean create(CrewMemberFlightRelationDto toCreate) {
    if (exists(toCreate)) {
      return false;
    }

    relationDao.insert(mapper.toEntity(toCreate));

    return true;
  }

  @Override
  public boolean exists(CrewMemberFlightRelationDto relation) {
    return relationDao.existsByCrewMemberAndFlightIds(relation.getCrewMemberId(), relation.getFlightId());
  }

  @Override
  public boolean delete(CrewMemberFlightRelationDto relation) {
    return relationDao.deleteByCrewMemberAndFlightIds(relation.getCrewMemberId(), relation.getFlightId());
  }

}
