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
@WebServlet(name = "updateCrewMemberServlet", value = "/crew-member/update")
public class UpdateCrewMemberServlet extends AbstractDomainServlet {

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    CrewMemberDto toUpdate = WebUtils.readBody(request, CrewMemberDto.class);
    log.info("Request on updating crew member. Crew member: {}", toUpdate);

    CrewMemberDto updated = crewMemberService.update(toUpdate);
    updated.createFlightsIfAbsent();

    WebUtils.sendJson(response, updated);
  }

}
