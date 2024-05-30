package com.airportmanagementbackend.common.dto.crewmember;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrewMemberListDto {

  private List<CrewMemberDto> crewMembers;

}
