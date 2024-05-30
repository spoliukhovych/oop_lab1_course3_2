package com.airportmanagementbackend.service.mapper;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.model.CrewMember;
import org.mapstruct.Mapper;

@Mapper
public abstract class CrewMemberMapper {

  public abstract CrewMemberDto toDto(CrewMember entity);

  public abstract CrewMember toEntity(CrewMemberDto dto);

}
