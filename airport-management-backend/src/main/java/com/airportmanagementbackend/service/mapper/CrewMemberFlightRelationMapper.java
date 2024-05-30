package com.airportmanagementbackend.service.mapper;

import com.airportmanagementbackend.common.dto.relation.CrewMemberFlightRelationDto;
import com.airportmanagementbackend.model.CrewMemberFlightRelation;
import org.mapstruct.Mapper;

@Mapper
public abstract class CrewMemberFlightRelationMapper {

  public abstract CrewMemberFlightRelationDto toDto(CrewMemberFlightRelation entity);

  public abstract CrewMemberFlightRelation toEntity(CrewMemberFlightRelationDto dto);

}
