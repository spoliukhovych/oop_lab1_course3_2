package com.airportmanagementbackend.web.crewmember;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.common.dto.crewmember.CrewMemberListDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "getAllCrewMembersServlet", value = "/crew-member/list")
public class GetAllCrewMembersServlet extends AbstractDomainServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    log.info("Request on retrieving all crew members");

    List<CrewMemberDto> crewMembers = crewMemberService.findAll();
    CrewMemberListDto result = new CrewMemberListDto(crewMembers);

    WebUtils.sendJson(response, result);
  }

}
