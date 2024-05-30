package com.airportmanagementbackend.web.crewmember;

import com.airportmanagementbackend.common.dto.crewmember.CrewMemberDto;
import com.airportmanagementbackend.util.WebUtils;
import com.airportmanagementbackend.web.AbstractDomainServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "createCrewMemberServlet", value = "/crew-member/create")
public class CreateCrewMemberServlet extends AbstractDomainServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    CrewMemberDto toCreate = WebUtils.readBody(request, CrewMemberDto.class);
    log.info("Request on creating crew member. Crew member: {}", toCreate);

    CrewMemberDto created = crewMemberService.create(toCreate);
    created.createFlightsIfAbsent();

    WebUtils.sendJson(response, created, HttpServletResponse.SC_CREATED);
  }

}
